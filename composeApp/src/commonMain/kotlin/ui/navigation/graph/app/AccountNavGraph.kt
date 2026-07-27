package ui.navigation.graph.app

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import ui.navigation.MainFlow
import ui.navigation.NavActions
import ui.navigation.graph.sharedNavGraph

fun NavGraphBuilder.accountNavGraph(navActions: NavActions) {
    navigation<MainFlow.Account>(
        startDestination = MainFlow.Account.startScreen::class
    ) {
        sharedNavGraph(navActions)
    }
}
