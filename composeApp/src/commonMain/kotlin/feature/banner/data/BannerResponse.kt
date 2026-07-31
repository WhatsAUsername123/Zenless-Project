/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.banner.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    @SerialName("banner_id")
    val id: Int,
    val title: String,
    val url: String,
    @SerialName("url_desc")
    val urlDesc: String,
    val route: String,
    @SerialName("route_desc")
    val routeDesc: String,
    val level: String,
    val ignorable: Boolean
) {
    fun getBannerLevel(): BannerLevel = when (level) {
        "warning" -> BannerLevel.Warning
        "alert" -> BannerLevel.Alert
        else -> BannerLevel.Normal
    }
}

enum class BannerLevel {
    Normal,
    Warning,
    Alert
}
