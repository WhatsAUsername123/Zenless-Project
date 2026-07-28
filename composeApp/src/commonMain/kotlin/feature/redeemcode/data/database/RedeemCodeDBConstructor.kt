package feature.redeemcode.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object RedeemedCodeDBConstructor : RoomDatabaseConstructor<RedeemedCodeDB> {
    override fun initialize(): RedeemedCodeDB
}
