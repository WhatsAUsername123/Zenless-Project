/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.dailytracker.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import feature.dailytracker.model.DailyTask
import org.koin.compose.viewmodel.koinViewModel
import ui.components.TopBarScaffold
import ui.components.ZzzSwitch
import ui.theme.AppTheme
import ui.utils.contentPaddingInScaffold

@Composable
fun DailyTrackerScreen(onBackClick: () -> Unit) {
    val viewModel: DailyTrackerViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(containerColor = AppTheme.colors.surface, topBar = {
        TopBarScaffold(
            title = "Daily Tracker",
            onBackClick = onBackClick
        )
    }) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .contentPaddingInScaffold(scaffoldPadding)
        ) {
            items(uiState.tasks) { task ->
                DailyTaskRow(
                    task = task,
                    onToggle = {
                        viewModel.onAction(
                            DailyTrackerAction.ToggleTask(
                                taskId = task.taskId,
                                currentlyDone = task.isDoneToday
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun DailyTaskRow(
    task: DailyTask,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.s300, vertical = AppTheme.spacing.s200),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = task.label,
            style = AppTheme.typography.titleSmall,
            color = AppTheme.colors.onSurface
        )
        ZzzSwitch(
            checkState = task.isDoneToday,
            onCheckChange = { onToggle() }
        )
    }
}
