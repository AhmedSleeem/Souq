package ahmed.adel.sleeem.clowyy.souq.room.cart;

import ahmed.adel.sleeem.clowyy.souq.room.FavouriteDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private  val cartRepo:CartRepositories;

    init {
        val itemDao = FavouriteDatabase.getDatabase(application).cartDao()
        cartRepo = CartRepositories(itemDao,application)
    }

   var userFavorites = cartRepo.userFavorites;

   suspend fun insert(cart:Cart) : Long {
        val row = viewModelScope.async {
            cartRepo.insertCartItem(cart)
        }
        return row.await()
    }

    fun update(cart:Cart) = viewModelScope.launch {
        cartRepo.updateCartItem(cart);
    }
    fun deleteAll() = viewModelScope.launch{
        cartRepo.deleteAll();
    }

    fun delete(id:String,itd:String) = viewModelScope.launch {
        cartRepo.deleteCartItem(id,itd);
    }


}