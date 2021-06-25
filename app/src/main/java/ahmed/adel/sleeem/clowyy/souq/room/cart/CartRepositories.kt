package ahmed.adel.sleeem.clowyy.souq.room.cart;



import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

class CartRepositories(private  val cartDAO: CartDAO,private val context: Context) {


    var userFavorites : LiveData<List<Cart>> =
        cartDAO.getCartItemsByUserId(LoginUtils.getInstance(context)!!.userInfo()._id.toString());



    suspend fun insertCartItem(item: Cart) : Long{
        return cartDAO.insertCartItem(item);
    }
    suspend fun deleteAll(){
        cartDAO.deleteAll();
    }


    suspend fun updateCartItem(item:Cart){
        cartDAO.updateCartItem(item)
    }

    
    suspend fun deleteCartItem(id:String,it:String){
        cartDAO.deleteCartItem(id,it)
    }
}