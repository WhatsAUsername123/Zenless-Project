/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package database

import androidx.room.RoomDatabase
import feature.agent.data.database.AgentsListDB
import feature.cover.data.database.CoverImagesListDB
import feature.dailytracker.data.database.DailyTaskDB
import feature.hoyolab.data.database.HoYoLabAccountDB

// Ref: Philipp Lackner’s YouTube Channel
expect class RoomDatabaseFactory {
    fun createAgentListDatabase(): RoomDatabase.Builder<AgentsListDB>

    fun createCoverImagesListDatabase(): RoomDatabase.Builder<CoverImagesListDB>

    fun createHoYoLabAccountDatabase(): RoomDatabase.Builder<HoYoLabAccountDB>

    fun createDailyTaskDatabase(): RoomDatabase.Builder<DailyTaskDB>
}
