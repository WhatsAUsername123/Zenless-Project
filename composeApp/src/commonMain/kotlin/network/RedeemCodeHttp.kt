package network

import feature.redeemcode.model.RemoteRedeemCode

interface RedeemCodeHttp {
    suspend fun requestRedeemCodes(): List<RemoteRedeemCode>
}
