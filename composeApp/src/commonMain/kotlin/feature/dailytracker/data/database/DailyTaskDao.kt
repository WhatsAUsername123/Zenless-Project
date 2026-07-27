package feature.dailytracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfNotExists(task: DailyTaskEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTask(task: DailyTaskEntity)
    @Query("SELECT * FROM DailyTaskEntity")
    fun getAllTasks(): Flow<List<DailyTaskEntity>>
    @Query("UPDATE DailyTaskEntity SET lastCompletedAt = :timestamp WHERE taskId = :taskId")
    suspend fun markCompleted(taskId: String, timestamp: Long)
}
