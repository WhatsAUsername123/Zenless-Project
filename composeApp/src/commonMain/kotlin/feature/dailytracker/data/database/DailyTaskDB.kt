package feature.dailytracker.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DailyTaskEntity::class],
    version = 1
)
@ConstructedBy(DailyTaskDBConstructor::class)
abstract class DailyTaskDB : RoomDatabase() {
    abstract val dailyTaskDao: DailyTaskDao

    companion object {
        const val DATABASE_NAME = "daily_task.db"
    }
}
