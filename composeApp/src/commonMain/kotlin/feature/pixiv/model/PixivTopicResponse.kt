/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.pixiv.model

import kotlinx.serialization.Serializable

@Serializable
data class PixivTopicResponse(val body: PixivBodyResponse? = null)

@Serializable
data class PixivBodyResponse(val illustManga: IllustMangaResponse? = null)

@Serializable
data class IllustMangaResponse(val data: List<RecentArticleResponse>? = null)

@Serializable
data class RecentArticleResponse(
    val id: String? = null,
    val title: String? = null,
    val url: String? = null,
    val userId: String? = null,
    val userName: String? = null,
    val profileImageUrl: String? = null
)

val stubPixivTopicResponse =
    PixivTopicResponse(
        PixivBodyResponse(
            IllustMangaResponse(
                listOf(
                    RecentArticleResponse(
                        id = "123456789",
                        title = "Ellen",
                        url = "https://i.pximg.net",
                        userId = "87654321",
                        userName = "mrfatworm",
                        profileImageUrl = "https://i.pximg.net"
                    )
                )
            )
        )
    )
