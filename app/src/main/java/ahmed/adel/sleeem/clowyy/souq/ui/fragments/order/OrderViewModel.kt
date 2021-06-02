package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.OrderResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<Resource<OrderResponse>>()
    val orders: MutableLiveData<Resource<OrderResponse>> get() = _orders


    fun getOrders(id : String) = viewModelScope.launch {
        _orders.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getOrders(id)
        if (response.isSuccessful) {
            if (response.body() != null)
                _orders.value = Resource.success(response.body()!!)
        } else {
            _orders.value = Resource.error(response.errorBody().toString())
        }
    }
}