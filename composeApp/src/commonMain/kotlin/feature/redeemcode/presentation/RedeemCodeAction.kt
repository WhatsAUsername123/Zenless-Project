package feature.redeemcode.presentation

import feature.redeemcode.model.RedeemCode

sealed interface RedeemCodeAction {
    data class ToggleRedeemed(val code: RedeemCode) : RedeemCodeAction
    data object Refresh : RedeemCodeAction
}
