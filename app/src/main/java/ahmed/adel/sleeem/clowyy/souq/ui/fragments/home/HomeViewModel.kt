package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.FilterParams
import ahmed.adel.sleeem.clowyy.souq.pojo.response.CategoryResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.repository.HomeRepository
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {

    private var _recommendedLiveData = MutableLiveData<Resource<ProductResponse>>()
    val recommendedLiveData: LiveData<Resource<ProductResponse>> get() = _recommendedLiveData

    val saleItemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val categoryLiveData = MutableLiveData<Resource<CategoryResponse>>()

    private var repository : HomeRepository =
        HomeRepository(
            RetrofitHandler.getItemWebService()
        )


    private var saleProductsResult: Flow<PagingData<ProductResponse.Item>>? = null
    private var recommendedResult: Flow<PagingData<ProductResponse.Item>>? = null

    fun getProductsHaveSale(): Flow<PagingData<ProductResponse.Item>> {
        val lastResult = saleProductsResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult = repository.saleRepos()
            .cachedIn(viewModelScope)
        saleProductsResult = newResult
        return newResult
    }

    fun getRecommended(params: FilterParams): Flow<PagingData<ProductResponse.Item>> {
        val lastResult = recommendedResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult = repository.recommendedRepos(params)
            .cachedIn(viewModelScope)
        recommendedResult = newResult
        return newResult
    }

    fun getSaleItems() = viewModelScope.launch{
        try {
            saleItemsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getSaleItems(1)
            if (response.isSuccessful){
                if(response.body() != null)
                    saleItemsLiveData.value = Resource.success(response.body()!!)
            }else{
                saleItemsLiveData.value = Resource.error(response.code().toString())
            }
        }catch (e:Exception){
            saleItemsLiveData.value = Resource.error(e.message.toString())
        }

    }


    fun getAllCategories() = viewModelScope.launch {
        try {
            categoryLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getCategory()
            if (response.isSuccessful) {
                if (response.body() != null)
                    categoryLiveData.value = Resource.success(response.body()!!);
            } else {
                categoryLiveData.value = Resource.error(response.code().toString())
            }
        }catch (e:Exception){
            categoryLiveData.value = Resource.error(e.message.toString())
        }
    }


}