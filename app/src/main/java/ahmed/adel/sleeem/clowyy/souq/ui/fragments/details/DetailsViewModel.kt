package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details

import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    val itemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val filterLiveData = MutableLiveData<Resource<ArrayList<ProductResponse.Item>>>()

    fun getItemsByCategory(category: String) = viewModelScope.launch {
        itemsLiveData.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getAllItems()

        if (response.isSuccessful) {
            var list: ProductResponse = response.body()!!
            val data = arrayListOf<ProductResponse.Item>()
            for (item in list) {
                if (item.category == category)
                    data.add(item)
            }
            filterLiveData.value = Resource.success(data = data)
        } else {
            itemsLiveData.value = Resource.error(response.errorBody().toString())
        }

    }

}