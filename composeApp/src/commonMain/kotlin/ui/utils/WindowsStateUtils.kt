/*
 *  Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 *  License: MIT License
 */

package ui.utils

import androidx.compose.runtime.Stable

@Stable
enum class AdaptiveLayoutType {
    Compact,
    Medium,
    Expanded
}

@Stable
enum class ContentType {
    Single,
    Dual
}
