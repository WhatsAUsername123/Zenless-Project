package ui.components

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.panpf.sketch.rememberAsyncImagePainter
import com.github.panpf.sketch.request.ComposableImageRequest
import com.mrfatworm.zzzarchive.composeApp.R
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.material3.HorizontalDivider

private data class GifSnapshot(
    val gifRes: Int,
    val archiveId: String,
    val title: String,
    val subtitle: String
)

@Composable
actual fun RandomGifFiller() {

    val snapshots = listOf(

        GifSnapshot(
            R.raw.punchline,
            "HDD-001",
            "The Punchline",
            "Recovered Footage"
        ),

        GifSnapshot(
            R.raw.paypal,
            "HDD-002",
            "Mommy Is Asking",
            "Confidential Record"
        ),

        GifSnapshot(
            R.raw.baddies,
            "HDD-003",
            "Refer To The Punchline As The Joke",
            "Restricted Archive"
        ),

        GifSnapshot(
            R.raw.google,
            "HDD-004",
            "The F*** You Searching",
            "Search History"
        )
    )

    var currentSnapshot by remember {
        mutableStateOf(snapshots.random())
    }

    var targetRotation by remember {
        mutableFloatStateOf(Random.nextFloat() * 10f - 5f)
    }

    val rotation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = spring(),
        label = "Rotation"
    )

    val cardScale = remember {
        Animatable(1f)
    }

    val recRotation = remember {
        Animatable(0f)
    }

    val scope = rememberCoroutineScope()

    val infinite = rememberInfiniteTransition(label = "REC")

    val recAlpha by infinite.animateFloat(
        initialValue = .35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "REC Alpha"
    )

    val context: Context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.82f)
                .graphicsLayer {
                    rotationZ = rotation
                    scaleX = cardScale.value
                    scaleY = cardScale.value
                    shadowElevation = 24.dp.toPx()
                }
                .clickable {

                    scope.launch {

                        var next: GifSnapshot

                        do {
                            next = snapshots.random()
                        } while (next == currentSnapshot)

                        currentSnapshot = next

                        targetRotation = Random.nextFloat() * 10f - 5f

                        // Card bounce
                        cardScale.animateTo(
                            0.96f,
                            animationSpec = tween(80)
                        )

                        cardScale.animateTo(
                            1f,
                            animationSpec = spring()
                        )

                        // REC wiggle
                        recRotation.animateTo(
                            -10f,
                            animationSpec = tween(60)
                        )

                        recRotation.animateTo(
                            10f,
                            animationSpec = tween(60)
                        )

                        recRotation.animateTo(
                            -5f,
                            animationSpec = tween(50)
                        )

                        recRotation.animateTo(
                            0f,
                            animationSpec = tween(60)
                        )
                    }
                },
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .background(Color(0xFF23252B))
                    .border(
                        1.2.dp,
                        Color(0xFFFF8C42).copy(alpha = .25f),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {

                //==========================
                // Header
                //==========================

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .graphicsLayer {
                                rotationZ = recRotation.value
                            }
                            .background(
                                Color(0xFFFF8C42).copy(alpha = recAlpha),
                                CircleShape
                            )
                    )

                    Spacer(Modifier.width(10.dp))

                    Column {

                        Text(
                            text = "● REC",
                            color = Color(0xFFFF8C42),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "H.D.D. ARCHIVE",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                HorizontalDivider(
                    color = Color(0xFF42464F)
                )

                Spacer(Modifier.height(16.dp))

                //==========================
                // GIF
                //==========================

                Crossfade(
                    targetState = currentSnapshot,
                    animationSpec = tween(450),
                    label = "ArchiveFade"
                ) { snapshot ->

                    val fadePainter = rememberAsyncImagePainter(
                        request = ComposableImageRequest(
                            uri = "android.resource://${context.packageName}/${snapshot.gifRes}"
                        )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .border(
                                1.dp,
                                Color(0xFF4E535D),
                                RoundedCornerShape(10.dp)
                            )
                            .clip(RoundedCornerShape(10.dp))
                    ) {

                        Image(
                            painter = fadePainter,
                            contentDescription = null,
                            modifier = Modifier.matchParentSize()
                        )

                        // Scanlines
                        Column(
                            modifier = Modifier.matchParentSize()
                        ) {

                            repeat(70) {

                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(
                                            Color.Black.copy(alpha = .02f)
                                        )
                                )

                                Spacer(
                                    Modifier.height(3.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Orange accent
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(Color(0xFFFF8C42))
                )

                Spacer(Modifier.height(14.dp))

                Text(
                    text = currentSnapshot.archiveId,
                    color = Color(0xFFFF8C42),
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = currentSnapshot.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = currentSnapshot.subtitle,
                    color = Color(0xFFB8BDC8)
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "STATUS : ONLINE",
                    color = Color(0xFF9EA3AE),
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Touch Archive Card To Load Another Record",
                    color = Color(0xFF80858F)
                )

                Spacer(Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        text = "Archive v1.0",
                        color = Color(0xFF666B74)
                    )
                }
            }
        }
    }
}
