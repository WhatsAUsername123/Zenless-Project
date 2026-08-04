/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.cover.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object CoverImagesListDBConstructor : RoomDatabaseConstructor<CoverImagesListDB> {
    override fun initialize(): CoverImagesListDB
}
