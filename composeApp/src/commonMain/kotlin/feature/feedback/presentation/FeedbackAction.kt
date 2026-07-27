/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.feedback.presentation

import feature.feedback.model.FeedbackIssueType

sealed interface FeedbackAction {
    data object SubmitForm : FeedbackAction

    data object DismissDialog : FeedbackAction

    data object ClickBack : FeedbackAction

    data class OnDescTextFieldChange(val value: String) : FeedbackAction

    data class OnEmailTextFieldChange(val value: String) : FeedbackAction

    data class OnSelectedIssueChange(val value: FeedbackIssueType) : FeedbackAction
}
