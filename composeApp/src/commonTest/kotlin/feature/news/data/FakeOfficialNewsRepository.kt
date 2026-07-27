/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.news.data

import feature.news.model.OfficialNewsResponse
import feature.news.model.stubOfficialNewsDataResponseResponse

class FakeOfficialNewsRepository : OfficialNewsRepository {
    private var isError = false

    fun setError(isError: Boolean) {
        this.isError = isError
    }

    override suspend fun getNews(
        amount: Int,
        languagePath: String
    ): Result<OfficialNewsResponse> = if (isError) {
        Result.failure(Exception())
    } else {
        Result.success(stubOfficialNewsDataResponseResponse)
    }
}
