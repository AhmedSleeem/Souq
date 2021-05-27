package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class SearchResultViewModel:ViewModel() {
    private var _productsLiveData = MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val productsLiveData:LiveData<Resource<ProductResponse>> get() = this._productsLiveData

    private var _filteredLiveData = MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val filteredLiveData:LiveData<Resource<ProductResponse>> get() = _filteredLiveData


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
                    ) { o1, o2 -> -(o1!!.price - o2!!.price).toInt() }
                    this._productsLiveData.value = Resource.success(data)
                }
            }

            SearchSucceedFragment.ShortBy.LowToHigh.tag->{
                if (data != null){
                    Collections.sort(data
                    ) { o1, o2 -> (o1!!.price - o2!!.price).toInt() }
                    this._productsLiveData.value = Resource.success(data)
                }
            }

            SearchSucceedFragment.ShortBy.TopRated.tag->{
                if (data != null){
                    Collections.sort(data
                    ) { o1, o2 -> -(o1!!.rating*10 - o2!!.rating*10).toInt() }
                    this._productsLiveData.value = Resource.success(data)
                }
            }
        }
    }

    fun getCategoriesAndCount():LiveData<List<Pair<String,Int>>>{
        val liveData = MutableLiveData<List<Pair<String,Int>>>()
        val data = getData()
        var categoryMap = mutableMapOf<String,Int>()
        if (data != null) {
            for (item in data){
                var count=0
                if (categoryMap.get(item.category.name)==null)
                    count=0
                else
                 count = categoryMap.get(item.category.name)!!
                categoryMap.put(item.category.name,++count)
            }
        }
        liveData.value =  categoryMap.toList()
        return liveData
    }

    fun filterByPrice(min:Int , max:Int){
        var data :ProductResponse?=null
        data = if (_filteredLiveData.value?.data != null)
            _filteredLiveData.value!!.data
        else
            getData()

        _filteredLiveData.value = Resource.loading(null)

        var filteredData = ProductResponse()
        if (data != null) {
            for (item in data){
                if (item.price >= min && item.price <= max){
                    filteredData.add(item)
                }
            }
        }
        filteredData.filteredByPrice = true
        _filteredLiveData.value = Resource.success(filteredData)
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

    fun filteredBySale(isBySale:Boolean){
        val filteredData = ProductResponse()
        var data :ProductResponse?=null

        data = if (_filteredLiveData.value?.data != null)
            _filteredLiveData.value!!.data
        else
            getData()

        if (isBySale) {
            if (data != null) {
                for (item in data) {
                    if (item.sale != null)
                        filteredData.add(item)
                }
            }
        }
        filteredData.filteredBySale = true
        _filteredLiveData.value = Resource.success(filteredData)
    }

    fun filterByCategory(category: String){
        var data :ProductResponse?=getData()
        var filteredData=ProductResponse()

        _filteredLiveData.value = Resource.loading(null)

        if (data != null) {
            for (item in data){
                if (item.category.name == category)
                    filteredData.add(item)
            }
        }
        filteredData.filteredByCategory = true
        _filteredLiveData.value = Resource.success(filteredData)
    }

    fun filterByBrands(brand: String){
        var data :ProductResponse?=null
        var filteredData=ProductResponse()
        data = if (_filteredLiveData.value?.data != null)
            _filteredLiveData.value!!.data
        else
            getData()

        _filteredLiveData.value = Resource.loading(null)

        if (data != null) {
            for (item in data){
                if (item.brand == brand)
                    filteredData.add(item)
            }
        }
        filteredData.filteredByBrand = true
        _filteredLiveData.value = Resource.success(filteredData)
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