package feature.dailytracker.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object DailyTaskDBConstructor : RoomDatabaseConstructor<DailyTaskDB> {
    override fun initialize(): DailyTaskDB
}
