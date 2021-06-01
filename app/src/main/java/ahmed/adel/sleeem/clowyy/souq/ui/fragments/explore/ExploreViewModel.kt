package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.api.ApiClient
import ahmed.adel.sleeem.clowyy.souq.pojo.CategoryResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExploreViewModel:ViewModel() {

    var categoriesLiveData = MutableLiveData<Resource<CategoryResponse>>()

    fun getCategories() = viewModelScope.launch {
        categoriesLiveData.value = Resource.loading(null)
        val response = ApiClient.apiService().getCategory()
        if (response.isSuccessful){
            if (response.body() != null)
                categoriesLiveData.value = Resource.success(response.body()!!)
        }else
            categoriesLiveData.value = Resource.error(response.errorBody().toString())
    }








}