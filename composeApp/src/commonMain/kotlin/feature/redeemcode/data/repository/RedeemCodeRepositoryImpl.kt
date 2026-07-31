package feature.redeemcode.data.repository

import feature.redeemcode.data.database.RedeemedCodeDao
import feature.redeemcode.data.database.RedeemedCodeEntity
import feature.redeemcode.model.RedeemCode
import feature.redeemcode.model.RemoteRedeemCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import network.RedeemCodeHttp
import kotlin.time.Clock

class RedeemCodeRepositoryImpl(
    private val http: RedeemCodeHttp,
    private val dao: RedeemedCodeDao
) : RedeemCodeRepository {

    private val remoteCodes = MutableStateFlow<List<RemoteRedeemCode>>(emptyList())

    override fun observeCodes(): Flow<List<RedeemCode>> =
        remoteCodes.combine(dao.getAllRedeemed()) { remote, redeemed ->
            val redeemedSet = redeemed.map { it.code }.toSet()
            remote.map { r ->
                RedeemCode(code = r.code, isRedeemed = r.code in redeemedSet)
            }.sortedBy { it.isRedeemed }
        }

    override suspend fun refresh() {
        remoteCodes.value = http.requestRedeemCodes()
    }

    override suspend fun markRedeemed(code: String) =
        dao.markRedeemed(RedeemedCodeEntity(code, Clock.System.now().toEpochMilliseconds()))

    override suspend fun unmarkRedeemed(code: String) = dao.unmarkRedeemed(code)
}
