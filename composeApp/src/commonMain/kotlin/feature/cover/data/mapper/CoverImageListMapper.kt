/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.cover.data.mapper

import com.mrfatworm.zzzarchive.ZzzConfig
import feature.cover.data.database.CoverImageListItemEntity
import feature.cover.model.CoverImageListItemResponse

fun CoverImageListItemResponse.toCoverImageListItemEntity(
    path: String = ZzzConfig.ASSET_PATH
): CoverImageListItemEntity = CoverImageListItemEntity(
    id = id,
    imageUrl = "https://raw.githubusercontent.com/$path/Banner/$id.webp",
    artworkUrl = artworkUrl,
    artworkName = artworkName,
    artworkDescription = artworkDescription,
    authorUrl = authorUrl,
    authorName = authorName
)
