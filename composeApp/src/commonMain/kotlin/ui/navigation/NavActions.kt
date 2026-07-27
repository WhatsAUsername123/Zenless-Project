/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package ui.navigation

import androidx.compose.runtime.Stable
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy // Added for robustness
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Stable
class NavActions(private val navController: NavHostController) {
    fun navigationTo(destination: Screen) {
        navController.navigate(destination)
    }

    fun back() {
        navController.popBackStack()
    }

    fun navigationToMainScreen(destination: MainFlow) {
        val isAlreadyInTargetFlow = navController.currentDestination?.hierarchy?.any { navDest ->
            navDest.hasRoute(destination::class)
        } == true

        if (isAlreadyInTargetFlow) {
            backToTopOfCurrentMainFlow(destination)
        } else {
            navController.navigate(destination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun popAndNavigation(destination: Screen) {
        navController.navigate(destination) {
            popUpTo(navController.graph.findStartDestination().id) {
                this.inclusive = true
            }
        }
    }

    private fun backToTopOfCurrentMainFlow(destination: MainFlow) {
        navController.popBackStack(route = destination.startScreen, inclusive = false)
    }
}
