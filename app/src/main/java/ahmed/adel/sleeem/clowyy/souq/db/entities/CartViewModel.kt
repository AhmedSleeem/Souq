package ahmed.adel.sleeem.clowyy.souq.db.entities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CartViewModel(private  val cartRepo:CartRepositories ):ViewModel() {



    fun insert(cart:Cart) = viewModelScope.launch {
            cartRepo.insertCartItem(cart);
        }

    fun update(cart:Cart) = viewModelScope.launch {
        cartRepo.updateCartItem(cart);
    }

    fun delete(cart:Cart) = viewModelScope.launch {
        cartRepo.deleteCartItem(cart);
    }


}