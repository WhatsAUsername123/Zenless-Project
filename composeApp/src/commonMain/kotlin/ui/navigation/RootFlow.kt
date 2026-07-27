/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootFlow {
    @Serializable
    data object Splash : Screen

    @Serializable
    data object MainContainer : Screen
}
