/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package network

import feature.pixiv.model.PixivTopicResponse
import feature.pixiv.model.stubPixivTopicResponse

class FakePixivHttp : PixivHttp {
    private var isError = false

    fun setError(isError: Boolean) {
        this.isError = isError
    }

    override suspend fun requestZzzTopic(zzzTag: String): PixivTopicResponse = if (isError) {
        throw Exception()
    } else {
        stubPixivTopicResponse
    }
}
