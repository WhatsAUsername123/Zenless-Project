package feature.redeemcode.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object RedeemedCodeDBConstructor : RoomDatabaseConstructor<RedeemedCodeDB> {
    override fun initialize(): RedeemedCodeDB
}
