package feature.dailytracker.data.database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyTaskEntity(
    @PrimaryKey val taskId: String,
    val label: String,
    val lastCompletedAt: Long
)
