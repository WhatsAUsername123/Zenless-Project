/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.hoyolab.domain

import feature.hoyolab.data.crypto.FakeZzzCrypto
import feature.hoyolab.data.database.FakeHoYoLabAccountDao
import feature.hoyolab.data.database.stubHoYoLabAccountEntity
import feature.hoyolab.data.repository.FakeHoYoLabAgentRepository
import feature.hoyolab.model.agent.stubMyAgentDetail
import feature.hoyolab.model.stubMyAgentsList
import feature.setting.data.FakePreferenceRepository
import feature.setting.domain.FakeLanguageUseCase
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import utils.AppFileStorage
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import feature.hoyolab.domain.debugLogPropertyIds

class HoYoLabAgentUseCaseTest {
    private val repository = FakeHoYoLabAgentRepository()
    private val accountDao = FakeHoYoLabAccountDao()
    private val preferencesRepository = FakePreferenceRepository()
    private val zzzCrypto = FakeZzzCrypto()
    private val languageUseCase = FakeLanguageUseCase()
    private val fileStorage = object : AppFileStorage {
        override val fileSystem: FileSystem get() = throw NotImplementedError()
        override fun cacheDirPath(): String = ""
    }
    private val useCase =
        HoYoLabAgentUseCase(
            repository = repository,
            accountDao = accountDao,
            preferencesRepository = preferencesRepository,
            zzzCrypto = zzzCrypto,
            languageUseCase = languageUseCase,
            fileStorage = fileStorage
        )

    @BeforeTest
    fun `Insert account to database`() = runTest {
        accountDao.insertAccount(stubHoYoLabAccountEntity.copy(uid = 1300051361))
        preferencesRepository.setDefaultHoYoLabAccountUid(1300051361)
    }

    @Test
    fun `Get agents list THEN success`() = runTest {
        val result = useCase.getAgentsList()
        assertEquals(Result.success(stubMyAgentsList), result)
    }

    @Test
    fun `Get agent detail THEN success`() = runTest {
        val result = useCase.getAgentDetail(1)
        assertEquals(Result.success(stubMyAgentDetail), result)
    }
}
