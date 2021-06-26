package ahmed.adel.sleeem.clowyy.souq.ui.fragments.offer

import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class OfferViewModel : ViewModel() {

    val itemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val filterLiveData = MutableLiveData<Resource<ArrayList<ProductResponse.Item>>>()

    fun getItemsBySale(saleType: String) = viewModelScope.launch {
        try {
            itemsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getSaleItems()

            if (response.isSuccessful) {
                var list: ProductResponse = response.body()!!
                val data = arrayListOf<ProductResponse.Item>()
                for (item in list) {
                    if (item.sale!!.type == saleType) {
                        data.add(item)
                    }
                }
                filterLiveData.value = Resource.success(data = data);
            } else {
                itemsLiveData.value = Resource.error(response.code().toString())
            }
        } catch (e: Exception) {
            itemsLiveData.value = Resource.error(e.message.toString())
        }
    }
}