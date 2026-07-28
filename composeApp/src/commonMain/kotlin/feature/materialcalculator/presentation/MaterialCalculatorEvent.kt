package feature.materialcalculator.presentation

sealed interface MaterialCalculatorEvent {

    data class CurrentLevelChanged(val level: Int) : MaterialCalculatorEvent

    data class TargetLevelChanged(val level: Int) : MaterialCalculatorEvent

    data object Calculate : MaterialCalculatorEvent
}
