/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.feedback.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.feedback.data.GoogleDocRepository
import feature.feedback.model.FeedbackIssueType
import feature.feedback.model.FeedbackState
import feature.feedback.model.feedbackIssueTypes
import feature.setting.domain.AppInfoUseCase
import feature.setting.domain.LanguageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zzzarchive.composeapp.generated.resources.Res
import zzzarchive.composeapp.generated.resources.invalid_feedback_form
import zzzarchive.composeapp.generated.resources.unknown_error

class FeedbackViewModel(
    private val appInfoUseCase: AppInfoUseCase,
    private val googleDocRepository: GoogleDocRepository,
    private val languageUseCase: LanguageUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(FeedbackState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeLanguage()
        }
        getAppVersion()
        getDeviceInfo()
        getDeviceOs()
    }

    fun onAction(action: FeedbackAction) {
        when (action) {
            is FeedbackAction.SubmitForm -> submitFeedback()

            FeedbackAction.DismissDialog -> dismissSubmitSuccessDialog()

            is FeedbackAction.OnDescTextFieldChange -> {
                _uiState.update { it.copy(issueTextFieldValue = action.value) }
            }

            is FeedbackAction.OnEmailTextFieldChange -> {
                _uiState.update { it.copy(emailTextFieldValue = action.value) }
            }

            is FeedbackAction.OnSelectedIssueChange -> {
                _uiState.update { it.copy(selectedIssue = action.value) }
            }

            else -> {}
        }
    }

    private fun submitFeedback() {
        val currentState = _uiState.value
        if (currentState.selectedIssue == feedbackIssueTypes.first() || currentState.issueTextFieldValue.isBlank()) {
            _uiState.update {
                it.copy(
                    invalidForm = true,
                    invalidMessage = Res.string.invalid_feedback_form
                )
            }
        } else {
            _uiState.update { it.copy(invalidForm = false) }
            viewModelScope.launch {
                postGoogleDoc(currentState.selectedIssue)
            }
        }
    }

    private suspend fun postGoogleDoc(issueType: FeedbackIssueType) {
        _uiState.update { it.copy(isLoading = true) }
        val result =
            googleDocRepository.submitFeedbackForm(
                issueType = issueType.chtString,
                language = uiState.value.language,
                issueDesc = uiState.value.issueTextFieldValue,
                email = uiState.value.emailTextFieldValue,
                appVersion = uiState.value.appVersion,
                deviceName = uiState.value.deviceName,
                operatingSystem = uiState.value.operatingSystem
            )
        result.fold(onSuccess = {
            _uiState.update {
                it.copy(
                    showSubmitSuccessDialog = true,
                    isLoading = false,
                    issueTextFieldValue = "",
                    emailTextFieldValue = "",
                    selectedIssue = feedbackIssueTypes.first()
                )
            }
        }, onFailure = {
            _uiState.update {
                it.copy(
                    invalidForm = true,
                    invalidMessage = Res.string.unknown_error,
                    isLoading = false
                )
            }
        })
    }

    private fun dismissSubmitSuccessDialog() {
        _uiState.update { it.copy(showSubmitSuccessDialog = false) }
    }

    private suspend fun observeLanguage() {
        languageUseCase.getLanguage().collect { language ->
            _uiState.update { it.copy(language = language.code) }
        }
    }

    private fun getAppVersion() {
        val appVersion = appInfoUseCase.getAppVersion()
        _uiState.update { it.copy(appVersion = appVersion) }
    }

    private fun getDeviceInfo() {
        val deviceName = appInfoUseCase.getDeviceInfo()
        _uiState.update { it.copy(deviceName = deviceName) }
    }

    private fun getDeviceOs() {
        val deviceOs = appInfoUseCase.getDeviceOs()
        _uiState.update { it.copy(operatingSystem = deviceOs) }
    }
}
