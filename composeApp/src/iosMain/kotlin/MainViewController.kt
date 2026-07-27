import androidx.compose.ui.window.ComposeUIViewController
import di.initKoin

fun mainViewController() = ComposeUIViewController(configure = {
    initKoin()
}) {
    ZzzArchiveApp()
}
