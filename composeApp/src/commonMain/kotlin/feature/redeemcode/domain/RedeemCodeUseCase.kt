package feature.redeemcode.domain

import feature.redeemcode.data.repository.RedeemCodeRepository
import feature.redeemcode.model.RedeemCode
import kotlinx.coroutines.flow.Flow

class RedeemCodeUseCase(private val repository: RedeemCodeRepository) {
    fun observeCodes(): Flow<List<RedeemCode>> = repository.observeCodes()
    suspend fun refresh() = repository.refresh()
    suspend fun toggleRedeemed(code: RedeemCode) {
        if (code.isRedeemed) repository.unmarkRedeemed(code.code)
        else repository.markRedeemed(code.code)
    }
}
