package feature.redeemcode.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.redeemcode.domain.RedeemCodeUseCase
import feature.redeemcode.model.RedeemCode
import feature.redeemcode.model.RedeemCodeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RedeemCodeViewModel(
    private val redeemCodeUseCase: RedeemCodeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RedeemCodeState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch { observeCodes() }
        refresh()
    }

    fun onAction(action: RedeemCodeAction) {
        when (action) {
            is RedeemCodeAction.ToggleRedeemed -> toggleRedeemed(action.code)
            is RedeemCodeAction.Refresh -> refresh()
        }
    }

    private fun toggleRedeemed(code: RedeemCode) {
        viewModelScope.launch { redeemCodeUseCase.toggleRedeemed(code) }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { redeemCodeUseCase.refresh() }
                .onFailure { e -> _uiState.update { it.copy(errorMessage = e.message) } }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun observeCodes() {
        redeemCodeUseCase.observeCodes().collect { codes ->
            _uiState.update { it.copy(codes = codes) }
        }
    }
}
