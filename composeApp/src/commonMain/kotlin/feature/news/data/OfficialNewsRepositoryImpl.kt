/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.news.data

import feature.news.model.OfficialNewsResponse
import network.OfficialWebHttp

class OfficialNewsRepositoryImpl(private val httpClient: OfficialWebHttp) : OfficialNewsRepository {
    override suspend fun getNews(
        amount: Int,
        languagePath: String
    ): Result<OfficialNewsResponse> = try {
        val result = httpClient.requestNews(amount, languagePath)
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
