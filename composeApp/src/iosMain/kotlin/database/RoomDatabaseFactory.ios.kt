/* Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package database

import androidx.room.Room
import androidx.room.RoomDatabase
import feature.agent.data.database.AgentsListDB
import feature.cover.data.database.CoverImagesListDB
import feature.hoyolab.data.database.HoYoLabAccountDB
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class RoomDatabaseFactory {
    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory =
            NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
        return requireNotNull(documentDirectory?.path)
    }

    actual fun createAgentListDatabase(): RoomDatabase.Builder<AgentsListDB> {
        val dbFile = documentDirectory() + "/${AgentsListDB.DATABASE_NAME}"
        return Room.databaseBuilder<AgentsListDB>(
            name = dbFile
        )
    }

    actual fun createCoverImagesListDatabase(): RoomDatabase.Builder<CoverImagesListDB> {
        val dbFile = documentDirectory() + "/${CoverImagesListDB.DATABASE_NAME}"
        return Room.databaseBuilder<CoverImagesListDB>(
            name = dbFile
        )
    }

    actual fun createHoYoLabAccountDatabase(): RoomDatabase.Builder<HoYoLabAccountDB> {
        val dbFile = documentDirectory() + "/${HoYoLabAccountDB.DATABASE_NAME}"
        return Room.databaseBuilder<HoYoLabAccountDB>(
            name = dbFile
        )
    }
}
