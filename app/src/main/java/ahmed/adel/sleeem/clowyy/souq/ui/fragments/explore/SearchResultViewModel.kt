package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.FilterParams
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog.ShortByBottomDialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.util.*

class SearchResultViewModel : ViewModel() {
    private var _productsLiveData =
        MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val productsLiveData: LiveData<Resource<ProductResponse>> get() = this._productsLiveData

    private var _categoriesLiveData = MutableLiveData<List<Pair<String, Int>>>()
    val categoriesLiveDirections: LiveData<List<Pair<String, Int>>> get() = _categoriesLiveData

    private var _itemsCountLiveData = MutableLiveData<Int>()
    val itemsCountLiveData: LiveData<Int> get() = _itemsCountLiveData


    fun shortData(shortBy: Int) {
        try {
            val data = getData()
            this._productsLiveData.value = Resource.loading(null)

            when (shortBy) {
                SearchSucceedFragment.ShortBy.BestMatch.tag -> {
                }

                SearchSucceedFragment.ShortBy.HighToLow.tag -> {
                    if (data != null) {
                        Collections.sort(
                            data
                        ) { o1, o2 -> -((o1!!.price * 100f - o2!!.price * 100f).toInt()) }

                    }
                }

                SearchSucceedFragment.ShortBy.LowToHigh.tag -> {
                    if (data != null) {
                        Collections.sort(
                            data
                        ) { o1, o2 -> ((o1!!.price * 100000).toInt() - (o2!!.price * 100000).toInt()) }

                    }

                }

                SearchSucceedFragment.ShortBy.TopRated.tag -> {
                    if (data != null) {
                        Collections.sort(
                            data
                        ) { o1, o2 -> -(o1!!.rating * 10 - o2!!.rating * 10).toInt() }

                    }
                }
            }

            _productsLiveData.value = Resource.success(data!!, 1)
        } catch (e: SocketTimeoutException) {
            _productsLiveData.value = Resource.error("No Internet Connection")
        } catch (ex: Exception) {
            _productsLiveData.value = Resource.error("Error happen while Searching. try again ")
        }

    }

    fun getCategoriesAndCount() {
        try {
            val data = getData()
            var categoryMap = mutableMapOf<String, Int>()
            if (data != null) {
                for (item in data) {

                    var count = if (categoryMap[item.category.name] == null)
                        0
                    else
                        categoryMap[item.category.name]!!
                    categoryMap[item.category.name] = ++count
                }
            }
            _categoriesLiveData.value = categoryMap.toList()
        } catch (e: SocketTimeoutException) {
            _productsLiveData.value = Resource.error("No Internet Connection")
        } catch (ex: Exception) {
            _productsLiveData.value = Resource.error("Error happen while Searching. try again ")
        }
    }

    fun getBrands(): LiveData<List<String>> {
        val liveData = MutableLiveData<List<String>>()
        val list = mutableSetOf<String>()
        val data = getData()
        if (data != null) {
            for (item in data) {
                list.add(item.brand)
            }
        }
        liveData.value = list.toList()
        return liveData
    }

    fun filterProducts(params: FilterParams) {
        viewModelScope.launch {
            try {
                _productsLiveData.value = Resource.loading(null);
                val response = RetrofitHandler.getItemWebService().filterProducts(
                    min = params.min,
                    max = params.max,
                    category = params.category,
                    sale = params.sale,
                    brand = params.brand,
                    price = params.price,
                    page = params.page
                )


                if (response.isSuccessful) {
                    ShortByBottomDialogFragment.position = -1
                    if (!response.body()!!.items!!.isNullOrEmpty()) {
                        _itemsCountLiveData.value = response.body()!!.count!!
                        _productsLiveData.value = Resource.success(response.body()!!.items!!, 1)
                    } else
                        _productsLiveData.value = Resource.error("not found")

                } else {
                    _productsLiveData.value = Resource.error(response.code().toString())
                }
            } catch (e: SocketTimeoutException) {
                _productsLiveData.value = Resource.error("No Internet Connection")
            } catch (ex: Exception) {
                _productsLiveData.value = Resource.error("Error happen while Searching. try again ")
            }
        }
    }


    private fun getData(): ProductResponse? {
        var data: ProductResponse? = null
        if (this._productsLiveData.value != Resource.loading(null)) {
            if (this._productsLiveData.value?.data != null) {
                data = this._productsLiveData.value!!.data
            }
        }
        return data;
    }

}