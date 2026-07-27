/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.cover.data

import feature.cover.data.database.FakeCoverImagesListDao
import feature.cover.data.repository.CoverImageRepositoryImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import network.FakeZzzHttp

class CoverImageRepositoryTest {
    private val httpClient = FakeZzzHttp()
    private val database = FakeCoverImagesListDao()
    private val repository = CoverImageRepositoryImpl(httpClient, database)
    // Remote: 2 Cover Images, Local: 1 Cover Image

    @Test
    fun `WHEN Get cover images list success THEN return local DB`() = runTest {
        val result = repository.getCoverImagesList().first()
        assertEquals(1, result.size)
    }

    @Test
    fun `WHEN Request cover images list success THEN return updated DB`() = runTest {
        repository.requestAndUpdateCoverImagesListDB()
        val result = repository.getCoverImagesList().first()
        assertEquals(2, result.size)
    }

    @Test
    fun `GIVEN cover images list DB is empty WHEN Get cover images list THEN Auto request and return updated DB`() =
        runTest {
            database.deleteCoverImagesList()
            val result = repository.getCoverImagesList().first()
            assertEquals(2, result.size)
        }

    @Test
    fun `WHEN Request cover images list error THEN return local DB`() = runTest {
        httpClient.setError(true)
        val result = repository.getCoverImagesList().first()
        assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN cover images list DB is empty WHEN Request cover images list error THEN return empty DB`() = runTest {
        httpClient.setError(true)
        database.deleteCoverImagesList()
        val result = repository.getCoverImagesList().first()
        assertEquals(0, result.size)
    }
}
