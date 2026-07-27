/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package feature.setting.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SystemConfigRepositoryImpl(private val dataStore: DataStore<Preferences>) : SystemConfigRepository {
    companion object {
        const val DEFAULT_BANNER_IGNORE_ID = 0
        const val DEFAULT_COVER_IMAGE_DB_VERSION = 0
        const val DEFAULT_AGENTS_LIST_DB_VERSION = 0
    }

    private val bannerIgnoreId = intPreferencesKey("banner_ignore_id")
    private val coverImageDBVersion = intPreferencesKey("cover_image_db_version")
    private val agentsListDBVersion = intPreferencesKey("agents_list_db_version")

    override fun getBannerIgnoreId(): Flow<Int> = dataStore.data.map {
        it[bannerIgnoreId] ?: DEFAULT_BANNER_IGNORE_ID
    }

    override suspend fun setBannerIgnoreId(value: Int) {
        dataStore.edit { it[bannerIgnoreId] = value }
    }

    override fun getCoverImageDBVersion(): Flow<Int> = dataStore.data.map {
        it[coverImageDBVersion] ?: DEFAULT_COVER_IMAGE_DB_VERSION
    }

    override suspend fun setCoverImageDBVersion(value: Int) {
        dataStore.edit { it[coverImageDBVersion] = value }
    }

    override fun getAgentListDBVersion(): Flow<Int> = dataStore.data.map {
        it[agentsListDBVersion] ?: DEFAULT_AGENTS_LIST_DB_VERSION
    }

    override suspend fun setAgentListDBVersion(value: Int) {
        dataStore.edit { it[agentsListDBVersion] = value }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
