package ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.gif.GifDecoder

@Composable
actual fun rememberGifImageLoader(): ImageLoader {
    val context = LocalPlatformContext.current
    return remember {
        ImageLoader.Builder(context)
            .components { add(GifDecoder.Factory()) }
            .build()
    }
}
