package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.DeleteOrderResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ReviewResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.UserResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    val itemsLiveData = MutableLiveData<Resource<ProductResponse>>()
    val filterLiveData = MutableLiveData<Resource<ArrayList<ProductResponse.Item>>>()

    private var _reviewsLiveData = MutableLiveData<Resource<ReviewResponse>>()
    val reviewsLiveData: LiveData<Resource<ReviewResponse>> get() = _reviewsLiveData

    private var _userLiveData = MutableLiveData<Resource<UserResponse>>()
    val userLiveData: LiveData<Resource<UserResponse>> get() = _userLiveData

    private var _reviewAvgLiveData = MutableLiveData<Pair<Int,Float>>()
    val reviewAvgLiveData: LiveData<Pair<Int,Float>> get() = _reviewAvgLiveData

    private val _item = MutableLiveData<Resource<ProductResponse.Item>>()
    val item: MutableLiveData<Resource<ProductResponse.Item>> get() = _item


    fun getReviewsByProductId(id:String){
        viewModelScope.launch {
            _reviewsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getReviewsByItemId(id = id)
            if (response.isSuccessful){
                if (response.body() != null) {
                    _reviewsLiveData.value = Resource.success(response.body()!!)
                    getAvgAndCount(response.body()!!)
                }
            }else{
                _reviewsLiveData.value = Resource.error(response.errorBody().toString());
            }
        }
    }

    fun getUserById(id:String){
        viewModelScope.launch {
            _userLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getUserById(id = id)
            if (response.isSuccessful){
                if (response.body() != null)
                    _userLiveData.value = Resource.success(response.body()!!)

            }else{
                _userLiveData.value = Resource.error(response.errorBody().toString());
            }
        }
    }

    private fun getAvgAndCount(data: ReviewResponse ) {
        var rating =0.0
        for (i in data){
            rating+=i.rating
        }
        val avg=((rating/2.0)/data.size).toFloat()
        _reviewAvgLiveData.value = Pair(data.size,avg)
    }

    fun getItemsByCategory(category: String) = viewModelScope.launch {
        try{
            itemsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsByCategory(category)

            if (response.isSuccessful) {
                var list: ProductResponse = response.body()!!
                val data = arrayListOf<ProductResponse.Item>()
                for (item in list) {
                    if (item.category.name == category)
                        data.add(item)
                }
                filterLiveData.value = Resource.success(data = data)
            } else {
                itemsLiveData.value = Resource.error("Error happend please try again")
            }
        }catch (ex : Exception){
            itemsLiveData.value = Resource.error("You have bad network")
        }


    }


    fun getItemsByIdFav(id: String) = viewModelScope.launch {
        try {
            _item.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsByIdFav(id)
            if (response.isSuccessful) {
                if (response.body() != null)
                    _item.value = Resource.success(response.body()!!)
            } else {
                _item.value = Resource.error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            _item.value = Resource.error(e.message.toString())
        }

    }

}