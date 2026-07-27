/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.hoyolab.data.repository

import feature.hoyolab.data.mapper.toMyAgentDetail
import feature.hoyolab.data.mapper.toMyAgentListItem
import feature.hoyolab.model.MyAgentListItem
import feature.hoyolab.model.agent.MyAgentDetail
import network.HoYoLabHttp

class HoYoLabAgentRepositoryImpl(private val httpClient: HoYoLabHttp) : HoYoLabAgentRepository {
    override suspend fun requestPlayerAgentList(
        languageCode: String,
        uid: Int,
        region: String,
        lToken: String,
        ltUid: String
    ): Result<List<MyAgentListItem>> = try {
        val result = httpClient.requestMyAgentList(
            languageCode = languageCode,
            uid = uid,
            region = region,
            lToken = lToken,
            ltUid = ltUid
        )
        Result.success(result.data?.avatarList?.map { it.toMyAgentListItem() } ?: emptyList())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun requestPlayerAgentDetail(
        languageCode: String,
        uid: Int,
        region: String,
        lToken: String,
        ltUid: String,
        agentId: Int
    ): Result<MyAgentDetail> = try {
        val result =
            httpClient.requestMyAgentDetail(
                languageCode = languageCode,
                uid = uid,
                region = region,
                agentId = agentId,
                lToken = lToken,
                ltUid = ltUid
            )
        result.data?.avatarList?.firstOrNull()?.let {
            Result.success(it.toMyAgentDetail())
        } ?: Result.failure(NoSuchElementException("Agent detail not found or data is null/empty"))
    } catch (e: Exception) {
        print("Error: $e")
        Result.failure(e)
    }
}
