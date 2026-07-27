package feature.hoyolab.data.repository

import feature.hoyolab.model.agent.stubMyAgentDetail
import feature.hoyolab.model.stubMyAgentsList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.test.runTest
import network.FakeHoYoLabHttp

class HoYoLabAgentRepositoryTest {
    private val httpClient = FakeHoYoLabHttp()
    private val repository = HoYoLabAgentRepositoryImpl(httpClient)

    @Test
    fun `Request agents list THEN success`() = runTest {
        val result = repository.requestPlayerAgentList(languageCode = "", uid = 0, region = "", lToken = "", ltUid = "")
        assertEquals(Result.success(stubMyAgentsList), result)
    }

    @Test
    fun `Request agents list THEN failure`() = runTest {
        httpClient.setError(true)
        val result = repository.requestPlayerAgentList(
            languageCode = "",
            uid = 0,
            region = "",
            lToken = "",
            ltUid = ""
        ).getOrNull()
        assertNull(result)
    }

    @Test
    fun `Request agent detail THEN success`() = runTest {
        val result = repository.requestPlayerAgentDetail(
            languageCode = "",
            uid = 0,
            region = "",
            lToken = "",
            ltUid = "",
            agentId = 0
        )
        assertEquals(Result.success(stubMyAgentDetail), result)
    }

    @Test
    fun `Request agent detail THEN failure`() = runTest {
        httpClient.setError(true)
        val result = repository.requestPlayerAgentDetail(
            languageCode = "",
            uid = 0,
            region = "",
            lToken = "",
            ltUid = "",
            agentId = 0
        ).getOrNull()
        assertNull(result)
    }
}
