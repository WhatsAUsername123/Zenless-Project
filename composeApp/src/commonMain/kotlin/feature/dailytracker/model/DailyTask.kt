package feature.dailytracker.model

data class DailyTask(
    val taskId: String,
    val label: String,
    val isDoneToday: Boolean
)
