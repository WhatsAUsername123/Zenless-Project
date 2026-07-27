/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.dailytracker.presentation

sealed interface DailyTrackerAction {
    data class ToggleTask(val taskId: String, val currentlyDone: Boolean) : DailyTrackerAction
}
