package feature.redeemcode.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import feature.redeemcode.model.RedeemCode
import org.koin.compose.viewmodel.koinViewModel
import ui.components.TopBarScaffold
import ui.components.buttons.ZzzIconButton
import ui.components.cards.ContentCard
import ui.theme.AppTheme
import ui.theme.LocalSnackbarController
import ui.utils.cardPadding
import ui.utils.contentGap
import ui.utils.contentPaddingInScaffold
import ui.utils.rememberSafeUriHandler
import zzzarchive.composeapp.generated.resources.Res
import zzzarchive.composeapp.generated.resources.ic_check_circle
import zzzarchive.composeapp.generated.resources.ic_copy
import zzzarchive.composeapp.generated.resources.ic_link
import zzzarchive.composeapp.generated.resources.ic_refresh

@Composable
fun RedeemCodeScreen(onBackClick: () -> Unit) {
    val viewModel: RedeemCodeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val clipboard = LocalClipboardManager.current
    val uriHandler = rememberSafeUriHandler()
    val snackbarController = LocalSnackbarController.current
    val copiedMessage = "Code copied to clipboard"

    Scaffold(
        containerColor = AppTheme.colors.surface,
        topBar = {
            TopBarScaffold(title = "Redeem Codes", onBackClick = onBackClick, actions = {
                ZzzIconButton(
                    iconRes = Res.drawable.ic_refresh,
                    size = AppTheme.size.iconButtonSmall,
                    onClick = { viewModel.onAction(RedeemCodeAction.Refresh) }
                )
            })
        },
        snackbarHost = { SnackbarHost(snackbarController.snackbarHostState) }
    ) { scaffoldPadding ->
        Box(modifier = Modifier.fillMaxSize().contentPaddingInScaffold(scaffoldPadding)) {
            when {
                uiState.isLoading && uiState.codes.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppTheme.colors.primary
                    )
                }

                uiState.errorMessage != null && uiState.codes.isEmpty() -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Couldn't load codes. Pull down to try again.",
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colors.onSurfaceVariant
                    )
                }

                uiState.codes.isEmpty() -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "No codes found right now",
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colors.onSurfaceVariant
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(contentGap())
                    ) {
                        items(uiState.codes, key = { it.code }) { code ->
                            RedeemCodeRow(
                                code = code,
                                onCopy = {
                                    clipboard.setText(AnnotatedString(code.code))
                                    snackbarController.showSnackbar(copiedMessage)
                                },
                                onRedeemOnWebsite = {
                                    uriHandler.openUri("https://zenless.hoyoverse.com/redemption/?code=${code.code}")
                                },
                                onToggle = { viewModel.onAction(RedeemCodeAction.ToggleRedeemed(code)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RedeemCodeRow(
    code: RedeemCode,
    onCopy: () -> Unit,
    onRedeemOnWebsite: () -> Unit,
    onToggle: () -> Unit
) {
    val contentAlpha = if (code.isRedeemed) 0.45f else 1f

    ContentCard(modifier = Modifier.fillMaxWidth(), hasDefaultPadding = false) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(cardPadding()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = code.code,
                    style = AppTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    ),
                    color = AppTheme.colors.onSurface.copy(alpha = contentAlpha)
                )
                code.rewards?.let {
                    Text(
                        text = it,
                        style = AppTheme.typography.bodySmall,
                        color = AppTheme.colors.onSurfaceVariant.copy(alpha = contentAlpha)
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.s200)) {
                ZzzIconButton(
                    iconRes = Res.drawable.ic_copy,
                    size = AppTheme.size.iconButtonSmall,
                    enabled = !code.isRedeemed,
                    onClick = onCopy
                )
                ZzzIconButton(
                    iconRes = Res.drawable.ic_link,
                    size = AppTheme.size.iconButtonSmall,
                    enabled = !code.isRedeemed,
                    onClick = onRedeemOnWebsite
                )
                ZzzIconButton(
                    iconRes = Res.drawable.ic_check_circle,
                    size = AppTheme.size.iconButtonSmall,
                    tint = if (code.isRedeemed) AppTheme.colors.primary else AppTheme.colors.onSurfaceVariant,
                    onClick = onToggle
                )
            }
        }
    }
}
