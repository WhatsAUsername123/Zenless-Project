/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.news.data

import feature.news.model.stubOfficialNewsDataResponseResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.test.runTest
import network.FakeOfficialWebHttp

class NewsRepositoryTest {
    private val httpClient = FakeOfficialWebHttp()
    private val repository = OfficialNewsRepositoryImpl(httpClient)

    @Test
    fun `Get news success`() = runTest {
        val result = repository.getNews(amount = 0, languagePath = "").getOrNull()
        assertEquals(stubOfficialNewsDataResponseResponse, result)
    }

    @Test
    fun `Get news error`() = runTest {
        httpClient.setError(true)
        val result = repository.getNews(amount = 0, languagePath = "").getOrNull()
        assertNull(result)
    }
}
