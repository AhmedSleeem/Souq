package ahmed.adel.sleeem.clowyy.souq.room.cart;

import ahmed.adel.sleeem.clowyy.souq.room.FavouriteDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private  val cartRepo:CartRepositories;

    init {
        val itemDao = FavouriteDatabase.getDatabase(application).cartDao()
        cartRepo = CartRepositories(itemDao,application)
    }

 public  var userFavorites = cartRepo.userFavorites;

   public fun insert(cart:Cart) = viewModelScope.launch {
            cartRepo.insertCartItem(cart);
        }

   public fun update(cart:Cart) = viewModelScope.launch {
        cartRepo.updateCartItem(cart);
    }

   public fun delete(cart:Cart) = viewModelScope.launch {
        cartRepo.deleteCartItem(cart);
    }


}