package ahmed.adel.sleeem.clowyy.souq.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "in_favourite_table")
data class IsInFavourite(
    @PrimaryKey
    val itemId: String,
    val userId: String
) : Serializable
