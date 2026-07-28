package feature.materialcalculator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import feature.materialcalculator.domain.MaterialCalculatorUseCase

class MaterialCalculatorViewModel(
    private val calculator: MaterialCalculatorUseCase
) : ViewModel() {

    var state by mutableStateOf(MaterialCalculatorState())
        private set

    fun onEvent(event: MaterialCalculatorEvent) {

        when (event) {

            is MaterialCalculatorEvent.CurrentLevelChanged ->
                state = state.copy(currentLevel = event.level)

            is MaterialCalculatorEvent.TargetLevelChanged ->
                state = state.copy(targetLevel = event.level)

            MaterialCalculatorEvent.Calculate -> {
                calculate()
            }
        }
    }

    private fun calculate() {

        // We'll implement this next.

    }
}
