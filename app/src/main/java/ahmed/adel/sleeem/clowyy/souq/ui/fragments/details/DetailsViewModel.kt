package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details

import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    val itemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val filterLiveData = MutableLiveData<Resource<ArrayList<ProductResponse.Item>>>()

    private val _item = MutableLiveData<Resource<ItemResponse>>()
    val item: MutableLiveData<Resource<ItemResponse>> get() = _item


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

    fun getItemsByCategory(category: String) = viewModelScope.launch {
        itemsLiveData.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getAllItems()

        if (response.isSuccessful) {
            var list: ProductResponse = response.body()!!
            val data = arrayListOf<ProductResponse.Item>()
            for (item in list) {
                if (item.category.name == category)
                    data.add(item)
            }
            filterLiveData.value = Resource.success(data = data)
        } else {
            itemsLiveData.value = Resource.error(response.errorBody().toString())
        }

    }

}