/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package feature.feedback.model

import org.jetbrains.compose.resources.StringResource
import zzzarchive.composeapp.generated.resources.Res
import zzzarchive.composeapp.generated.resources.crash_or_bug
import zzzarchive.composeapp.generated.resources.incorrect_content
import zzzarchive.composeapp.generated.resources.other
import zzzarchive.composeapp.generated.resources.please_select
import zzzarchive.composeapp.generated.resources.suggestion
import zzzarchive.composeapp.generated.resources.unknown_error

data class FeedbackState(
    val issueTypes: List<FeedbackIssueType> = feedbackIssueTypes,
    val language: String = "",
    val appVersion: String = "",
    val deviceName: String = "",
    val operatingSystem: String = "",
    val showSubmitSuccessDialog: Boolean = false,
    val invalidForm: Boolean = false,
    val invalidMessage: StringResource = Res.string.unknown_error,
    val isLoading: Boolean = false,
    val issueTextFieldValue: String = "",
    val emailTextFieldValue: String = "",
    val selectedIssue: FeedbackIssueType = feedbackIssueTypes.first()
)

data class FeedbackIssueType(val localStringRes: StringResource, val chtString: String)

val feedbackIssueTypes =
    listOf(
        FeedbackIssueType(localStringRes = Res.string.please_select, chtString = "未選擇類型"),
        FeedbackIssueType(localStringRes = Res.string.crash_or_bug, chtString = "閃退或 Bug"),
        FeedbackIssueType(localStringRes = Res.string.incorrect_content, chtString = "文本、內容錯誤"),
        FeedbackIssueType(localStringRes = Res.string.suggestion, chtString = "建議"),
        FeedbackIssueType(localStringRes = Res.string.other, chtString = "其他")
    )
