@Composable
fun MaterialCalculatorScreen(
    viewModel: MaterialCalculatorViewModel
) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Material Calculator")

        Spacer(Modifier.height(16.dp))

        Text("Current Level")

        Slider(
            value = state.currentLevel.toFloat(),
            onValueChange = {
                viewModel.onEvent(
                    MaterialCalculatorEvent.CurrentLevelChanged(
                        it.toInt()
                    )
                )
            },
            valueRange = 1f..60f
        )

        Text("${state.currentLevel}")

        Spacer(Modifier.height(16.dp))

        Text("Target Level")

        Slider(
            value = state.targetLevel.toFloat(),
            onValueChange = {
                viewModel.onEvent(
                    MaterialCalculatorEvent.TargetLevelChanged(
                        it.toInt()
                    )
                )
            },
            valueRange = 1f..60f
        )

        Text("${state.targetLevel}")

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.onEvent(MaterialCalculatorEvent.Calculate)
            }
        ) {
            Text("Calculate")
        }

    }

}
