/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.pixiv.model

data class PixivArticleItem(
    val id: String,
    val title: String,
    val artworkUrl: String,
    val artworkImageUrl: String,
    val profileId: String,
    val profileName: String,
    val profileUrl: String,
    val profileImageUrl: String
)
