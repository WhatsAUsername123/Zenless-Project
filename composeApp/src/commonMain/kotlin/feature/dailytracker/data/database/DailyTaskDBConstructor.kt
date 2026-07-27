package feature.dailytracker.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object DailyTaskDBConstructor : RoomDatabaseConstructor<DailyTaskDB> {
    override fun initialize(): DailyTaskDB
}
