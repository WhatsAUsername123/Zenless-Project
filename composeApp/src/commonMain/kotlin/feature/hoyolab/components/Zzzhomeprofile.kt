package feature.hoyolab.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.panpf.sketch.SubcomposeAsyncImage
import com.github.panpf.sketch.request.ComposableImageRequest
import com.github.panpf.sketch.util.Size
import feature.hoyolab.model.GameRecordState
import feature.home.presentation.HomeProfileStats
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.components.ImageNotFound
import ui.components.buttons.ZzzOutlineButton
import ui.theme.AppTheme
import zzzarchive.composeapp.generated.resources.*

@Composable
fun ZzzProfileHeader(
    gameRecord: GameRecordState,
    level: Int?,
    signResult: String?,
    onSignClick: () -> Unit,
    onAddAccountClick: () -> Unit
) {
    Box(Modifier.fillMaxWidth().height(160.dp).clip(AppTheme.shape.r400)) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            request = ComposableImageRequest(gameRecord.cardUrl) { size(Size.Origin) },
            contentDescription = stringResource(Res.string.user_profile_image),
            contentScale = ContentScale.Crop,
            error = {
                if (gameRecord.hasAccount) ImageNotFound()
                else Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Res.drawable.img_hoyolab_card_preview),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        )
        // scrim so text stays readable on bright art
        Box(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC000000)))
            )
        )
        Box(Modifier.align(Alignment.TopEnd).padding(AppTheme.spacing.s300)) {
            SignButton(signResult = signResult, onSignClick = onSignClick)
        }
        Row(
            modifier = Modifier.align(Alignment.BottomStart).padding(AppTheme.spacing.s400),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.s350),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(2.dp, AppTheme.colors.accent, CircleShape),
                uri = gameRecord.profileUrl,
                contentDescription = stringResource(Res.string.user_profile_image),
                error = { ImageNotFound() }
            )
            Column {
                Text(
                    text = gameRecord.nickname,
                    style = AppTheme.typography.zzzHeading,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = listOfNotNull(
                        level?.let { "Lv.$it" },
                        gameRecord.server,
                        "UID ${gameRecord.uid}"
                    ).joinToString("  |  "),
                    style = AppTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (!gameRecord.hasAccount) {
            Box(Modifier.fillMaxSize().background(AppTheme.colors.hoveredMask))
            ZzzOutlineButton(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(Res.string.add_account),
                iconRes = Res.drawable.ic_link
            ) { onAddAccountClick() }
        }
    }
}

@Composable
fun ZzzStatsPanel(gameRecord: GameRecordState, stats: HomeProfileStats) {
    val energy = gameRecord.energy
    val isFull = energy.progress.current == energy.progress.max
    Column(
        Modifier
            .fillMaxWidth()
            .clip(AppTheme.shape.r400)
            .background(AppTheme.colors.accent)
            .padding(AppTheme.spacing.s350)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(AppTheme.shape.r400)
                .background(AppTheme.colors.onAccent)
                .padding(vertical = AppTheme.spacing.s400),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ZzzStatItem(stats.activeDays?.toString() ?: "-", "Days Active")
            ZzzStatItem(stats.deadlyAssaultLayer?.toString() ?: "-", "Deadly Assault")
            ZzzStatItem(stats.shiyuLayers?.toString() ?: "-", "Shiyu Defense")
        }
        Spacer(Modifier.height(AppTheme.spacing.s350))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = "${energy.progress.current}/${energy.progress.max}",
                    style = AppTheme.typography.zzzNumber,
                    color = AppTheme.colors.onAccent
                )
                Text(
                    text = if (isFull) "Full" else "Full at ${energy.hour}:${energy.minute}",
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colors.onAccent.copy(alpha = 0.6f)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${gameRecord.vitality.current}/${gameRecord.vitality.max}",
                    style = AppTheme.typography.zzzNumber,
                    color = AppTheme.colors.onAccent
                )
                Text(
                    text = "Engagement",
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colors.onAccent.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun RowScope.ZzzStatItem(value: String, label: String) {
    Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = AppTheme.typography.zzzNumber,
            color = AppTheme.colors.accent
        )
        Text(
            text = label,
            style = AppTheme.typography.labelSmall,
            color = Color.White,
            maxLines = 1
        )
    }
}
