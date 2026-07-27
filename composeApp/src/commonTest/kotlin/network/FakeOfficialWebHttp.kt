/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package network

import feature.news.model.OfficialNewsResponse
import feature.news.model.stubOfficialNewsDataResponseResponse

class FakeOfficialWebHttp : OfficialWebHttp {
    private var isError = false

    fun setError(isError: Boolean) {
        this.isError = isError
    }

    override suspend fun requestNews(
        amount: Int,
        languagePath: String
    ): OfficialNewsResponse = if (isError) {
        throw Exception()
    } else {
        stubOfficialNewsDataResponseResponse
    }
}
