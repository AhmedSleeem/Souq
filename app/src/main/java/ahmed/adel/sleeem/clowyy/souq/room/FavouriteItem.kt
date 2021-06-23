package ahmed.adel.sleeem.clowyy.souq.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourite_table")
data class FavouriteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val itemId: String,
    val userId: String?,
    var productName: String,
    var productImage: String,
    val rating: Float,
    val price: Double,
    val offer: Int,
) : Serializable
