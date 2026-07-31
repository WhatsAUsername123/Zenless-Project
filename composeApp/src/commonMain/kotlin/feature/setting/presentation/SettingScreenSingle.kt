/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.setting.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import feature.setting.components.OtherInfoCard
import feature.setting.components.SettingCard
import feature.setting.model.SettingState
import ui.components.RandomGifFiller
import ui.utils.contentGap
import ui.utils.verticalSafePadding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun SettingScreenSingle(
    uiState: SettingState,
    onAction: (SettingAction) -> Unit
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(verticalSafePadding()),
        verticalArrangement = Arrangement.spacedBy(contentGap())
    ) {
        SettingCard(
            uiState = uiState,
            onLanguageChange = {
                onAction(SettingAction.ChangeLanguage(it))
            },
            onColorChange = {
                onAction(SettingAction.ChangeToDarkTheme(it))
            },
            onScaleChange = { uiScale, fontScale ->
                onAction(SettingAction.ScaleUi(uiScale, fontScale))
            }
        ) {
            onAction(SettingAction.RestartApp)
        }

        OtherInfoCard(
            onFeedbackClick = {
                onAction(SettingAction.ClickFeedback)
            },
            onDailyTrackerClick = {
                onAction(SettingAction.ClickDailyTracker)
            }
        )

        androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                RandomGifFiller()
            }    }
}
