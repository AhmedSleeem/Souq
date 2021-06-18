package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart

import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.request.OrderRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.OrderResponse

import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse

import ahmed.adel.sleeem.clowyy.souq.utils.CartRoom
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class CartViewModel : ViewModel() {

    private var _cartItemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val cartItemsLiveData: LiveData<Resource<ProductResponse>> get() = _cartItemsLiveData

    private var _productsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val productsLiveData: LiveData<Resource<ProductResponse>> get() = _productsLiveData

    private var _addOrderLiveData = MutableLiveData<Resource<OrderResponse>>()
    val addOrderLiveData: LiveData<Resource<OrderResponse>> get() = _addOrderLiveData


    private var productResponse = ProductResponse()

    fun getCartItems() {
        _cartItemsLiveData.value = Resource.loading(null)
        _cartItemsLiveData.value = Resource.success(CartRoom.cartList)
        productResponse = CartRoom.cartList
        getQuantity()
    }

    private fun getQuantity() {
        for (i in productResponse.indices) {
            getItemsByTitle(productResponse[i].title, i)
        }
    }

    private fun getItemsByTitle(query: String, position: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitHandler.getItemWebService().getItemsByTitle(query)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        productResponse[position].quantity = response.body()!![0].quantity
                        _cartItemsLiveData.value = Resource.success(productResponse)
                    }
                } else
                    _cartItemsLiveData.value = Resource.error(response.errorBody().toString())
            } catch (e: SocketTimeoutException) {
                _cartItemsLiveData.value = Resource.error("Please chack internet connection..")
            } catch (ex: Exception) {
                _cartItemsLiveData.value = Resource.error("Process Faild ")
            }

        }
    }

    fun addNewOrder(orderRequest: OrderRequest) = viewModelScope.launch {
        try {
            _addOrderLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().addOrder(orderRequest)
            if (response.isSuccessful) {
                if (response.body() != null)
                    _addOrderLiveData.value = Resource.success(response.body()!!)
            } else {
                _addOrderLiveData.value = Resource.error(response.errorBody().toString())
            }
        } catch (e: SocketTimeoutException) {
            _addOrderLiveData.value = Resource.error("Please chack internet connection..")
        } catch (ex: Exception) {
            _addOrderLiveData.value = Resource.error("Process Faild ")
        }
    }

}