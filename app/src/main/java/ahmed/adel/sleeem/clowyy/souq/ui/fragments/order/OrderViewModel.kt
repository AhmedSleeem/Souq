package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.response.OrdersByIdResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<Resource<OrdersByIdResponse>>()
    val orders: MutableLiveData<Resource<OrdersByIdResponse>> get() = _orders


    fun getOrders(id : String) = viewModelScope.launch {
        try {
            _orders.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getOrders(id)
            if (response.isSuccessful) {
                if (response.body() != null)
                    _orders.value = Resource.success(response.body()!!)
            } else {
                _orders.value = Resource.error("You have no order ")
            }
        }catch (e:SocketTimeoutException){
            _orders.value = Resource.error(e.message.toString())
        }catch (e:Exception){
            _orders.value = Resource.error("Connecting Failed, Please Try Again...")
        }

    }
}