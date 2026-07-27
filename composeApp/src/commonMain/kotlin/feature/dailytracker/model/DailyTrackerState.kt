/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.dailytracker.model

data class DailyTrackerState(
    val tasks: List<DailyTask> = emptyList()
)
