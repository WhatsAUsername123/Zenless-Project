package feature.materialcalculator.presentation

import feature.materialcalculator.model.MaterialCalculationResult

data class MaterialCalculatorState(
    val currentLevel: Int = 1,
    val targetLevel: Int = 60,

    val result: MaterialCalculationResult? = null,

    val loading: Boolean = false
)
