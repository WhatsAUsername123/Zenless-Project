package feature.redeemcode.model

data class RedeemCodeState(
    val codes: List<RedeemCode> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
