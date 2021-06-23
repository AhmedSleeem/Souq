package ahmed.adel.sleeem.clowyy.souq.room.cart;


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CartDAO {


    @Insert
    suspend fun insertCartItem(item: Cart);

    @Update
    suspend fun updateCartItem(item:Cart);

    @Query("delete from CART_TABLE where userId = :id and itemId = :itd")
    suspend fun deleteCartItem(id:String , itd:String);

    @Query("delete from CART_TABLE ")
    suspend fun deleteAll()



    @Query("select * from CART_TABLE WHERE userId = :id ")
    fun getCartItemsByUserId(id:String):LiveData<List<Cart>>
}