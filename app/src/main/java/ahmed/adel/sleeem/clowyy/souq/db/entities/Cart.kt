package ahmed.adel.sleeem.clowyy.souq.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart_Table")
data class Cart(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int,

    @ColumnInfo(name= "itemId")
    val itemId:Int,

    @ColumnInfo(name = "itemName")
    val itemName:String,

    @ColumnInfo(name="count",defaultValue = "0")
    var count:Int,

    @ColumnInfo(name="price")
    val price:Double,

    @ColumnInfo(name = "itemImage")
    val itemImage:String,

    @ColumnInfo(name = "isLiked")
    val isLiked:Boolean,

    @ColumnInfo(name = "userId")
    val userId:String

    )
