/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.cover.domain

import feature.cover.data.repository.CoverImageRepository

class CoverImageUseCase(private val repository: CoverImageRepository) {
    suspend fun invoke() = repository.getCoverImagesList()

    suspend fun updateCoverImagesList() = repository.requestAndUpdateCoverImagesListDB()
}
