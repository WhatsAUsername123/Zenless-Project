/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.agent.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object AgentsListDBConstructor : RoomDatabaseConstructor<AgentsListDB> {
    override fun initialize(): AgentsListDB
}
