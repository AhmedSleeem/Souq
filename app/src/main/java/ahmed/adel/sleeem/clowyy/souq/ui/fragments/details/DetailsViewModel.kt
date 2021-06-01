package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details

import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ReviewResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.UserResponse
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

    fun getReviewsByProductId(id:String){
        viewModelScope.launch {
            _reviewsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getReviewsByItemId(id = id)
            if (response.isSuccessful){
                if (response.body() != null)
                    _reviewsLiveData.value = Resource.success(response.body()!!)
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