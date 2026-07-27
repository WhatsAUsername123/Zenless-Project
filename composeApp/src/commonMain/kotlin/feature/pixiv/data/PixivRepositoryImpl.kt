/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package feature.pixiv.data

import feature.pixiv.data.mapper.toPixivArticleList
import feature.pixiv.model.PixivArticleItem
import feature.pixiv.model.PixivTopicResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import network.PixivHttp

class PixivRepositoryImpl(private val httpClient: PixivHttp) : PixivRepository {
    private val pixivArticleList = MutableStateFlow<List<PixivArticleItem>>(emptyList())

    override fun getZzzTopic(): Flow<List<PixivArticleItem>> = pixivArticleList

    override suspend fun updateZzzTopic(zzzTag: String): Result<PixivTopicResponse> = try {
        val result = httpClient.requestZzzTopic(zzzTag)
        val articleList = result.toPixivArticleList()
        pixivArticleList.value = articleList
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
