/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package ui.components.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import ui.navigation.NAV_BOTTOM_MAIN_FLOW
import ui.navigation.NavActions
import ui.theme.AppTheme

@Composable
fun ZzzArchiveBottomNavigationBar(
    selectedMainFlow: NavDestination?,
    navigationActions: NavActions
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppTheme.colors.surface
    ) {
        NAV_BOTTOM_MAIN_FLOW.forEach { mainFlow ->
            val isSelected = selectedMainFlow?.hierarchy?.any {
                it.hasRoute(route = mainFlow.route::class)
            } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navigationActions.navigationToMainScreen(mainFlow.route)
                },
                icon = {
                    Icon(
                        imageVector = vectorResource(mainFlow.iconRes),
                        contentDescription = stringResource(mainFlow.textRes)
                    )
                },
                label = {
                    Text(
                        text = stringResource(mainFlow.textRes),
                        style = if (isSelected) AppTheme.typography.labelMedium else AppTheme.typography.labelSmall
                    )
                },
                colors = navigationBarItemColors()
            )
        }
    }
}

@Composable
private fun navigationBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = AppTheme.colors.onPrimaryContainer,
    selectedTextColor = AppTheme.colors.onSurface,
    indicatorColor = AppTheme.colors.primaryContainer,
    unselectedIconColor = AppTheme.colors.onSurfaceVariant,
    unselectedTextColor = AppTheme.colors.onSurfaceVariant
)
