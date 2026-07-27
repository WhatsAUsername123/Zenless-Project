/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ui.components.dialogs.BannerDialog
import ui.navigation.Screen
import ui.theme.AppTheme
import ui.utils.ContentType

@Composable
fun HomeScreen(navigateTo: (Screen) -> Unit) {
    val viewModel: HomeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(uiState, onAction = { actions ->
        when (actions) {
            is HomeAction.NavigateTo -> {
                navigateTo(actions.route)
            }

            else -> viewModel.onAction(actions)
        }
    })
}

@Composable
private fun HomeScreenContent(
    uiState: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val banner = uiState.banner
    val openBannerDialog = remember { mutableStateOf(false) }
    if (AppTheme.contentType == ContentType.Single) {
        HomeScreenSingle(uiState = uiState, onAction = onAction, onOpenBannerDialog = {
            openBannerDialog.value = true
        })
    } else {
        HomeScreenDual(uiState = uiState, onAction = onAction, onOpenBannerDialog = {
            openBannerDialog.value = true
        })
    }
    when {
        openBannerDialog.value -> {
            BannerDialog(
                message = banner?.title ?: "",
                url = banner?.url ?: "",
                urlDesc = banner?.urlDesc ?: "",
                onDismiss = { openBannerDialog.value = false }
            )
        }
    }
}
