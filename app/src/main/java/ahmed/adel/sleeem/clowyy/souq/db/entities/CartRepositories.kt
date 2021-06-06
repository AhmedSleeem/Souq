package ahmed.adel.sleeem.clowyy.souq.db.entities

import ahmed.adel.sleeem.clowyy.souq.db.entities.dao.CartDAO
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

class CartRepositories(private  val cartDAO: CartDAO) {

    fun getCartItemBy(userId:String){
        cartDAO.getCartItemsByUserId(userId);
    }


    suspend fun insertCartItem(item: Cart){
        cartDAO.insertCartItem(item);
    }


    suspend fun updateCartItem(item:Cart){
        cartDAO.updateCartItem(item)
    }

    
    suspend fun deleteCartItem(item:Cart){
        cartDAO.deleteCartItem(item)
    }
}