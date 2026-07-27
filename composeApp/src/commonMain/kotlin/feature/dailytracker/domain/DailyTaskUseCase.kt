/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.dailytracker.domain

import feature.dailytracker.data.database.DailyTaskDao
import feature.dailytracker.data.database.DailyTaskEntity
import feature.dailytracker.model.DailyTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

class DailyTaskUseCase(private val dao: DailyTaskDao) {

    fun getDailyTasks(): Flow<List<DailyTask>> =
        dao.getAllTasks().map { entities ->
            entities.map { it.toDailyTask() }
        }

    suspend fun toggleTaskDone(taskId: String, currentlyDone: Boolean) {
        val timestamp = if (currentlyDone) 0L else Clock.System.now().toEpochMilliseconds()
        dao.markCompleted(taskId, timestamp)
    }

    suspend fun seedDefaultTasksIfNeeded() {
        val defaultTasks = listOf(
            DailyTaskEntity(taskId = "daily_login", label = "Daily Check-In", lastCompletedAt = 0L),
            DailyTaskEntity(taskId = "combat_sim", label = "Combat Simulation", lastCompletedAt = 0L),
            DailyTaskEntity(taskId = "scratch_card", label = "Scratch Card", lastCompletedAt = 0L)
        )
        defaultTasks.forEach { dao.insertIfNotExists(it) }
    }

    private fun DailyTaskEntity.toDailyTask(): DailyTask {
        val timeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.now().toLocalDateTime(timeZone).date
        val isDone = if (lastCompletedAt == 0L) {
            false
        } else {
            val completedDate = Instant.fromEpochMilliseconds(lastCompletedAt)
                .toLocalDateTime(timeZone).date
            completedDate == today
        }
        return DailyTask(
            taskId = taskId,
            label = label,
            isDoneToday = isDone
        )
    }
}
