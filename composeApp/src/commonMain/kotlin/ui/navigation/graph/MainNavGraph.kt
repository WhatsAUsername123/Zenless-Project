/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package ui.navigation.graph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ui.navigation.MainFlow
import ui.navigation.NavActions
import ui.navigation.graph.app.agentNavGraph
import ui.navigation.graph.app.homeNavGraph
import ui.navigation.graph.app.settingNavGraph

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navActions: NavActions,
    rootNavActions: NavActions
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainFlow.Home,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeNavGraph(navActions)
        agentNavGraph(navActions)
        settingNavGraph(navActions)
    }
}
