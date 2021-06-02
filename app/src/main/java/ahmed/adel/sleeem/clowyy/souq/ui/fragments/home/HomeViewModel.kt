package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home

import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.response.CategoryResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {

    val itemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val saleItemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val categoryLiveData = MutableLiveData<Resource<CategoryResponse>>()



    fun getItems() = viewModelScope.launch {
        itemsLiveData.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getAllItems()
        if (response.isSuccessful){
            if(response.body() != null)
                itemsLiveData.value = Resource.success(response.body()!!);
        }else{
            itemsLiveData.value = Resource.error(response.errorBody().toString())
        }
    }


    fun getSaleItems() = viewModelScope.launch {
        saleItemsLiveData.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getSaleItems()
        if (response.isSuccessful){
            if(response.body() != null)
                saleItemsLiveData.value = Resource.success(response.body()!!);
        }else{
            saleItemsLiveData.value = Resource.error(response.errorBody().toString())
        }
    }


    fun getAllCategories() = viewModelScope.launch {
        categoryLiveData.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getCategory()
        if (response.isSuccessful){
            if(response.body() != null)
                categoryLiveData.value = Resource.success(response.body()!!);
        }else{
            categoryLiveData.value = Resource.error(response.errorBody().toString())
        }
    }


}