/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package database

import androidx.room.Room
import androidx.room.RoomDatabase
import feature.agent.data.database.AgentsListDB
import feature.cover.data.database.CoverImagesListDB
import feature.hoyolab.data.database.HoYoLabAccountDB
import java.io.File

actual class RoomDatabaseFactory {
    private inline fun <reified T : RoomDatabase> createDB(databaseName: String): RoomDatabase.Builder<T> {
        val os = System.getProperty("os.name").lowercase()
        val appDataDir =
            when {
                os.contains("win") -> File(System.getenv("APPDATA"), "ZZZ Archive")
                else -> File(System.getProperty("java.io.tmpdir"), databaseName)
            }

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, databaseName)
        return Room.databaseBuilder(dbFile.absolutePath)
    }

    actual fun createAgentListDatabase(): RoomDatabase.Builder<AgentsListDB> = createDB(AgentsListDB.DATABASE_NAME)

    actual fun createCoverImagesListDatabase(): RoomDatabase.Builder<CoverImagesListDB> =
        createDB(CoverImagesListDB.DATABASE_NAME)

    actual fun createHoYoLabAccountDatabase(): RoomDatabase.Builder<HoYoLabAccountDB> =
        createDB(HoYoLabAccountDB.DATABASE_NAME)
}
