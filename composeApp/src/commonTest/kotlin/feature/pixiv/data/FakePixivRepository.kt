/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.pixiv.data

import feature.pixiv.data.mapper.toPixivArticleList
import feature.pixiv.model.PixivArticleItem
import feature.pixiv.model.PixivTopicResponse
import feature.pixiv.model.stubPixivTopicResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePixivRepository : PixivRepository {
    private var isError = false
    private val pixivArticleList =
        MutableStateFlow(
            stubPixivTopicResponse.toPixivArticleList()
        )

    override fun getZzzTopic(): Flow<List<PixivArticleItem>> = pixivArticleList

    fun setError(isError: Boolean) {
        this.isError = isError
    }

    override suspend fun updateZzzTopic(zzzTag: String): Result<PixivTopicResponse> = if (isError) {
        Result.failure(Exception())
    } else {
        val articleList = stubPixivTopicResponse.toPixivArticleList()
        pixivArticleList.value = articleList
        Result.success(stubPixivTopicResponse)
    }
}
