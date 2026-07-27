/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package ui.navigation

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import zzzarchive.composeapp.generated.resources.Res
import zzzarchive.composeapp.generated.resources.agents
import zzzarchive.composeapp.generated.resources.home
import zzzarchive.composeapp.generated.resources.ic_help
import zzzarchive.composeapp.generated.resources.ic_home
import zzzarchive.composeapp.generated.resources.ic_people
import zzzarchive.composeapp.generated.resources.ic_setting
import zzzarchive.composeapp.generated.resources.setting
import zzzarchive.composeapp.generated.resources.unknown

@Serializable
sealed class MainFlow(val startScreen: Screen) {
    @Serializable
    data object Home : MainFlow(
        startScreen = Screen.Home
    )

    @Serializable
    data object Agent : MainFlow(
        startScreen = Screen.AgentsList
    )

    @Serializable
    data object Setting : MainFlow(
        startScreen = Screen.Setting
    )
}

data class TopDestination(
    val route: MainFlow,
    val iconRes: DrawableResource = Res.drawable.ic_help,
    val textRes: StringResource = Res.string.unknown
)

private val topHomeDestination = TopDestination(
    route = MainFlow.Home,
    iconRes = Res.drawable.ic_home,
    textRes = Res.string.home
)

private val topAgentDestination = TopDestination(
    route = MainFlow.Agent,
    iconRes = Res.drawable.ic_people,
    textRes = Res.string.agents
)

private val topSettingDestination = TopDestination(
    route = MainFlow.Setting,
    iconRes = Res.drawable.ic_setting,
    textRes = Res.string.setting
)

val NAV_RAIL_MAIN_FLOW =
    listOf(
        topHomeDestination,
        topAgentDestination,
        topSettingDestination
    )

val NAV_BOTTOM_MAIN_FLOW =
    listOf(
        topHomeDestination,
        topAgentDestination,
        topSettingDestination
    )
