/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package feature.setting.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import ui.components.RandomGifFiller
import ui.components.cards.ContentCard
import ui.theme.AppTheme
import ui.utils.openUriSafe
import zzzarchive.composeapp.generated.resources.Res
import zzzarchive.composeapp.generated.resources.feedback
import zzzarchive.composeapp.generated.resources.ic_arrow_next_ios
import zzzarchive.composeapp.generated.resources.privacy_policy

@Composable
fun OtherInfoCard(
    onFeedbackClick: () -> Unit,
    onDailyTrackerClick: () -> Unit
) {
    ContentCard(hasDefaultPadding = false) {
        DailyTrackerItem(onClick = onDailyTrackerClick)
        PrivacyPolicyItem()
    }
}


@Composable
private fun DailyTrackerItem(onClick: () -> Unit) {
    SettingItem(title = "Daily Tracker", content = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.s300)
        ) {
            Icon(
                modifier = Modifier.size(AppTheme.size.iconSmall),
                imageVector = vectorResource(Res.drawable.ic_arrow_next_ios),
                contentDescription = null,
                tint = AppTheme.colors.onSurfaceVariant
            )
        }
    }, onClick = onClick)
}
@Composable
private fun PrivacyPolicyItem() {
    val urlHandler = LocalUriHandler.current
    SettingItem(title = stringResource(Res.string.privacy_policy), content = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.s300)
        ) {
            Icon(
                modifier = Modifier.size(AppTheme.size.iconSmall),
                imageVector = vectorResource(Res.drawable.ic_arrow_next_ios),
                contentDescription = null,
                tint = AppTheme.colors.onSurfaceVariant
            )
        }
    }, onClick = {
        urlHandler.openUriSafe("https://youtu.be/mC9KYvOPmxA?si=lWLqwZUavuOwjQiB")
    })
}
