package feature.dailytracker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.dailytracker.domain.DailyTaskUseCase
import feature.dailytracker.model.DailyTrackerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DailyTrackerViewModel(
    private val dailyTaskUseCase: DailyTaskUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DailyTrackerState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dailyTaskUseCase.seedDefaultTasksIfNeeded()
        }
        viewModelScope.launch {
            observeTasks()
        }
    }

    fun onAction(action: DailyTrackerAction) {
        when (action) {
            is DailyTrackerAction.ToggleTask -> toggleTask(action.taskId, action.currentlyDone)
        }
    }

    private fun toggleTask(taskId: String, currentlyDone: Boolean) {
        viewModelScope.launch {
            dailyTaskUseCase.toggleTaskDone(taskId, currentlyDone)
        }
    }

    private suspend fun observeTasks() {
        dailyTaskUseCase.getDailyTasks().collect { tasks ->
            _uiState.update { it.copy(tasks = tasks) }
        }
    }
}
