package feature.redeemcode.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import feature.redeemcode.model.RedeemCode
import org.jetbrains.compose.resources.vectorResource
import ui.components.cards.CardHeader
import ui.components.cards.ContentCard
import ui.theme.AppTheme
import ui.utils.cardPaddingWithHeader
import zzzarchive.composeapp.generated.resources.Res
import zzzarchive.composeapp.generated.resources.ic_arrow_next_ios
import zzzarchive.composeapp.generated.resources.ic_award_star

@Composable
fun RedeemCodeCard(codes: List<RedeemCode>, onClick: () -> Unit) {
    val activeCount = codes.count { !it.isRedeemed }

    ContentCard(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        hasDefaultPadding = false
    ) {
        CardHeader(
            title = "Redeem Codes",
            startContent = {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_award_star),
                    contentDescription = null,
                    tint = AppTheme.colors.primary
                )
            },
            endContent = {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_arrow_next_ios),
                    contentDescription = null,
                    tint = AppTheme.colors.onSurfaceVariant
                )
            }
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(cardPaddingWithHeader()),
            text = if (activeCount > 0) {
                "$activeCount active code${if (activeCount == 1) "" else "s"} waiting to be redeemed"
            } else {
                "No active codes right now"
            },
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant
        )
    }
}
