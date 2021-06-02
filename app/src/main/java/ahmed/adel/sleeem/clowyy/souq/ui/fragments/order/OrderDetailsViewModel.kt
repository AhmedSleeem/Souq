package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.itemResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class OrderDetailsViewModel : ViewModel() {

    private val _item = MutableLiveData<Resource<itemResponse>>()
    val item: MutableLiveData<Resource<itemResponse>> get() = _item


    fun getItemsById(id: String) = viewModelScope.launch {
        _item.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getItemsById(id)
        if (response.isSuccessful) {
            if (response.body() != null)
                _item.value = Resource.success(response.body()!!)
        } else {
            _item.value = Resource.error(response.errorBody().toString())
        }
    }
}