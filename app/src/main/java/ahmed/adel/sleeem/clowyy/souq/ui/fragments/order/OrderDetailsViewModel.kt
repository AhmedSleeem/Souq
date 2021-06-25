package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.DeleteOrderRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.DeleteOrderResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class OrderDetailsViewModel : ViewModel() {

    private val _item = MutableLiveData<Resource<ItemResponse>>()
    val item: MutableLiveData<Resource<ItemResponse>> get() = _item

    private val _deleteOrder = MutableLiveData<Resource<DeleteOrderResponse>>()
    val deleteOrder: MutableLiveData<Resource<DeleteOrderResponse>> get() = _deleteOrder


    fun getItemsById(id: String) = viewModelScope.launch {
        try {
            _item.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsById(id)
            if (response.isSuccessful) {
                if (response.body() != null)
                    _item.value = Resource.success(response.body()!!)
            } else {
                _item.value = Resource.error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            _item.value = Resource.error(e.message.toString())
        }

    }

    fun deleteOrderById(deleteOrderRequest: DeleteOrderRequest) = viewModelScope.launch {
        try {
            deleteOrder.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().deleteOrderById(deleteOrderRequest)
            if (response.isSuccessful) {
                if (response.body() != null)
                    deleteOrder.value = Resource.success(response.body()!!)
            } else {
                deleteOrder.value = Resource.error("Deleted Failed")
            }
        } catch (e: Exception) {
            deleteOrder.value = Resource.error("Internet Connection Bad")
        }

    }
}