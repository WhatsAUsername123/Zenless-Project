/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import feature.agent.data.database.AgentsListDB
import feature.cover.data.database.CoverImagesListDB
import feature.dailytracker.data.database.DailyTaskDB
import feature.hoyolab.data.database.HoYoLabAccountDB
import feature.redeemcode.data.database.RedeemedCodeDB

actual class RoomDatabaseFactory(private val context: Context) {
    private fun <T : RoomDatabase> createDB(
        databaseClass: Class<T>,
        databaseName: String
    ): RoomDatabase.Builder<T> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(databaseName)

        return Room.databaseBuilder(
            context = appContext,
            klass = databaseClass,
            name = dbFile.absolutePath
        )
    }

    actual fun createRedeemCodeDatabase(): RoomDatabase.Builder<RedeemedCodeDB> =
        createDB(RedeemedCodeDB::class.java, RedeemedCodeDB.DATABASE_NAME)

    actual fun createAgentListDatabase(): RoomDatabase.Builder<AgentsListDB> =
        createDB(AgentsListDB::class.java, AgentsListDB.DATABASE_NAME)

    actual fun createCoverImagesListDatabase(): RoomDatabase.Builder<CoverImagesListDB> =
        createDB(CoverImagesListDB::class.java, CoverImagesListDB.DATABASE_NAME)

    actual fun createHoYoLabAccountDatabase(): RoomDatabase.Builder<HoYoLabAccountDB> =
        createDB(HoYoLabAccountDB::class.java, HoYoLabAccountDB.DATABASE_NAME)

    actual fun createDailyTaskDatabase(): RoomDatabase.Builder<DailyTaskDB> =
        createDB(DailyTaskDB::class.java, DailyTaskDB.DATABASE_NAME)
}
