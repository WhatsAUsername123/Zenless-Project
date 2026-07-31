package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.github.panpf.sketch.rememberAsyncImagePainter
import com.github.panpf.sketch.request.ComposableImageRequest
import kotlin.random.Random
import com.mrfatworm.zzzarchive.composeApp.R
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn

@Composable
actual fun RandomGifFiller() {

    val context = LocalContext.current

    val gifs = listOf(
        R.raw.punchline,
        R.raw.paypal,
        R.raw.baddies,
        R.raw.google
    )

    var currentGif by remember {
        mutableStateOf(gifs.random())
    }

    val painter = rememberAsyncImagePainter(
        ComposableImageRequest(
            "android.resource://${context.packageName}/raw/$currentGif"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .clickable {
                    var next: Int
                    do {
                        next = gifs[Random.nextInt(gifs.size)]
                    } while (next == currentGif)

                    currentGif = next
                }
        )
    }
}
