package ahmed.adel.sleeem.clowyy.souq.room.cart;

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Cart_Table" , indices = arrayOf(Index(value = ["itemId", "userId"],
    unique = true)))
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

    @ColumnInfo(name = "userId")
    val userId:String,

    @Nullable
    @ColumnInfo(name = "color")
    val color:String,

    @Nullable
    @ColumnInfo(name = "size")
    val size:String,

    @ColumnInfo(name = "companyName")
    val companyName:String,



    )
