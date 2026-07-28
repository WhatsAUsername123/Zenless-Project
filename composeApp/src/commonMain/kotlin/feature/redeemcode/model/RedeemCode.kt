package feature.redeemcode.model

data class RedeemCode(
    val code: String,
    val isRedeemed: Boolean,
    val rewards: String? = null,  // scraper doesn't provide this — kept nullable for future manual enrichment
    val expiresAt: String? = null // same — no expiry data available from the source
)
