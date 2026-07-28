package network

import feature.redeemcode.model.RemoteRedeemCode
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class RedeemCodeHttpImpl(engine: HttpClientEngine) : RedeemCodeHttp {
    private val client = createRedeemCodeHttpClient(engine)

    override suspend fun requestRedeemCodes(): List<RemoteRedeemCode> {
        val result = client.get(
            "https://raw.githubusercontent.com/WhatsAUsername123/Python/main/codes.json"
        ).bodyAsText()
        return Json { ignoreUnknownKeys = true }.decodeFromString(result)
    }
}
