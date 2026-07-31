package feature.redeemcode.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RedeemedCodeEntity::class], version = 1)
@ConstructedBy(RedeemedCodeDBConstructor::class)
abstract class RedeemedCodeDB : RoomDatabase() {
    abstract val redeemedCodeDao: RedeemedCodeDao

    companion object {
        const val DATABASE_NAME = "redeemed_code.db"
    }
}
