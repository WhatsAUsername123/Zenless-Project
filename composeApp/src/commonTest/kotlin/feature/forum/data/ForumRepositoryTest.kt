package feature.forum.data

import feature.forum.model.stubAllForumResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.test.runTest
import network.FakeForumHttp

class ForumRepositoryTest {
    private val httpClient = FakeForumHttp()
    private val repository = ForumRepositoryImpl(httpClient)

    @Test
    fun `Get all forum list`() = runTest {
        val result = repository.getAllForumList().getOrNull()
        assertEquals(stubAllForumResponse, result)
    }

    @Test
    fun `Get all forum list fail`() = runTest {
        httpClient.setError(true)
        val result = repository.getAllForumList().getOrNull()
        assertNull(result)
    }
}
