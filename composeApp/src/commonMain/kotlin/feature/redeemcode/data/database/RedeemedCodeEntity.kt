package feature.redeemcode.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RedeemedCodeEntity(
    @PrimaryKey val code: String,
    val redeemedAt: Long
)
