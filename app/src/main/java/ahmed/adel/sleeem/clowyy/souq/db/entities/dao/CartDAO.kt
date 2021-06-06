package ahmed.adel.sleeem.clowyy.souq.db.entities.dao

import ahmed.adel.sleeem.clowyy.souq.db.entities.Cart
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CartDAO {

    @Insert
    suspend fun insertCartItem(item: Cart);

    @Update
    suspend fun updateCartItem(item:Cart);

    @Delete
    suspend fun deleteCartItem(item:Cart);


    @Query("select * from CART_TABLE WHERE userId = :id ")
    fun getCartItemsByUserId(id:String):LiveData<List<Cart>>
}