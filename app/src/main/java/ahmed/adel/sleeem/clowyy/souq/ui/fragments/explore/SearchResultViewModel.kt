package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.FilterParams
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.Comparator

class SearchResultViewModel:ViewModel() {
    private var _productsLiveData = MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val productsLiveData:LiveData<Resource<ProductResponse>> get() = this._productsLiveData

    private var _categoriesLiveData = MutableLiveData<List<Pair<String,Int>>>()
    val categoriesLiveDirections:LiveData<List<Pair<String,Int>>> get() = _categoriesLiveData



    fun getItemsByQuery(query: String){
        viewModelScope.launch {
            this@SearchResultViewModel._productsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsByTitle(query)
            if(response.isSuccessful){
                if (response.body() != null)
                    this@SearchResultViewModel._productsLiveData.value = Resource.success(response.body()!!)
            }else
                    this@SearchResultViewModel._productsLiveData.value = Resource.error(response.errorBody().toString())
        }
    }

    fun getItemsByCategory(query: String) {
        viewModelScope.launch {
        this@SearchResultViewModel._productsLiveData.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getItemsByCategory(query)
        if(response.isSuccessful){
            if (response.body() != null)
                this@SearchResultViewModel._productsLiveData.value = Resource.success(response.body()!!)
        }else
                this@SearchResultViewModel._productsLiveData.value = Resource.error(response.errorBody().toString())
        }
    }

    fun shortData(shortBy: Int) {
        val data = getData()
        this._productsLiveData.value = Resource.loading(null)

        when(shortBy){
            SearchSucceedFragment.ShortBy.BestMatch.tag->{
            }

            SearchSucceedFragment.ShortBy.HighToLow.tag->{
                if (data != null){
                    Collections.sort(data
                    ) { o1, o2 -> -((o1!!.price*100f - o2!!.price*100f).toInt()) }

                }
            }

            SearchSucceedFragment.ShortBy.LowToHigh.tag->{
                if (data != null){
                    Collections.sort(data
                    ) { o1, o2 -> ((o1!!.price*100000).toInt() - (o2!!.price*100000).toInt()) }

                }

            }

            SearchSucceedFragment.ShortBy.TopRated.tag->{
                if (data != null){
                    Collections.sort(data
                    ) { o1, o2 -> -(o1!!.rating*10 - o2!!.rating*10).toInt() }

                }
            }
        }

        _productsLiveData.value = Resource.success(data!!,1)
    }

    fun getCategoriesAndCount(){
        val data = getData()
        var categoryMap = mutableMapOf<String,Int>()
        if (data != null) {
            for (item in data){
                var count=0
                count = if (categoryMap[item.category.name] ==null)
                    0
                else
                    categoryMap.get(item.category.name)!!
                categoryMap[item.category.name] = ++count
            }
        }
        _categoriesLiveData.value =  categoryMap.toList()
    }

    fun getBrands():LiveData<List<String>>{
        val liveData = MutableLiveData<List<String>>()
        val list = mutableSetOf<String>()
        val data = getData()
        if (data != null) {
            for (item in data){
                list.add(item.brand)
            }
        }
         liveData.value = list.toList()
        return liveData
    }

    fun filterProducts(params:FilterParams){
        viewModelScope.launch {
            _productsLiveData.value = Resource.loading(null);
            val response = RetrofitHandler.getItemWebService().filterProducts(
                min = params.min,
                max = params.max,
                category = params.category,
                sale= params.sale,
                brand= params.brand,
                price = params.price
            )
            

            if (response.isSuccessful){
                if (response.body() != null)
                    _productsLiveData.value = Resource.success(response.body()!!,1)
            }else{
                _productsLiveData.value = Resource.error(response.errorBody().toString())
            }
        }
    }


    private fun getData():ProductResponse?{
        var data:ProductResponse?=null
        if (this._productsLiveData.value != Resource.loading(null)){
            if (this._productsLiveData.value?.data != null){
                data = this._productsLiveData.value!!.data
            }
        }
        return  data;
    }

}