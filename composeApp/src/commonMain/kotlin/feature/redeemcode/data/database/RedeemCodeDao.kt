package feature.redeemcode.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RedeemedCodeDao {
    @Query("SELECT * FROM RedeemedCodeEntity")
    fun getAllRedeemed(): Flow<List<RedeemedCodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markRedeemed(entity: RedeemedCodeEntity)

    @Query("DELETE FROM RedeemedCodeEntity WHERE code = :code")
    suspend fun unmarkRedeemed(code: String)
}
