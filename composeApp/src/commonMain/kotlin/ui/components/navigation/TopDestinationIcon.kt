/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package ui.components.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.panpf.sketch.AsyncImage
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import ui.navigation.TopDestination
import utils.composeResourceUri

/**
 * Renders a nav destination's icon. Uses an animated gif via Sketch when
 * [TopDestination.animatedIconPath] is set (drop the file at
 * composeApp/src/commonMain/composeResources/<path>), otherwise falls back
 * to the static [TopDestination.iconRes] vector.
 */
@Composable
fun TopDestinationIcon(
    destination: TopDestination,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    val contentDescription = stringResource(destination.textRes)
    val animatedIconPath = destination.animatedIconPath
    if (animatedIconPath != null) {
        AsyncImage(
            uri = composeResourceUri(animatedIconPath),
            contentDescription = contentDescription,
            modifier = modifier.size(24.dp)
        )
    } else {
        Icon(
            imageVector = vectorResource(destination.iconRes),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
    }
}
