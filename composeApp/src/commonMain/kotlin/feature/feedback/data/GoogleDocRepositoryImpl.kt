/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package feature.feedback.data

import network.GoogleDocHttp

class GoogleDocRepositoryImpl(private val httpClient: GoogleDocHttp) : GoogleDocRepository {
    override suspend fun submitFeedbackForm(
        issueType: String,
        language: String,
        issueDesc: String,
        email: String,
        appVersion: String,
        deviceName: String,
        operatingSystem: String
    ): Result<Unit> = try {
        httpClient.submitFeedbackForm(
            issueType = issueType,
            language = language,
            issueContent = issueDesc,
            email = email,
            appVersion = appVersion,
            deviceName = deviceName,
            operatingSystem = operatingSystem
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
