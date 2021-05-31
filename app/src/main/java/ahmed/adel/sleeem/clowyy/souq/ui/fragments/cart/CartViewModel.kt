package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart

import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog.ShortByBottomDialogFragment
import ahmed.adel.sleeem.clowyy.souq.utils.CartRoom
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

   private var _cartItemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val cartItemsLiveData : LiveData<Resource<ProductResponse>> get() = _cartItemsLiveData

    private var _productsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val productsLiveData : LiveData<Resource<ProductResponse>> get() = _productsLiveData

    fun getCartItems(){
        _cartItemsLiveData.value = Resource.loading(null)
        _cartItemsLiveData.value = Resource.success(CartRoom.cartList)

    }

    fun getItemsBytitle(query: String){
        viewModelScope.launch {
            _productsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsByTitle(query)
            if(response.isSuccessful){
                ShortByBottomDialogFragment.position = -1
                if (response.body() != null)
                    _productsLiveData.value = Resource.success(response.body()!!)
            }else
                _productsLiveData.value = Resource.error(response.errorBody().toString())
        }
    }

}