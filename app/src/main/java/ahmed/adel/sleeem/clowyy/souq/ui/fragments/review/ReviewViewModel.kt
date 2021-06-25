package ahmed.adel.sleeem.clowyy.souq.ui.fragments.review

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.FullUserInfo
import ahmed.adel.sleeem.clowyy.souq.pojo.request.DeleteReviewRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.request.ModifyReviewRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.request.ReviewRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.DeleteReviewResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ReviewResponse
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class ReviewViewModel(application: Application):AndroidViewModel(application) {


    private var _reviewsLiveData = MutableLiveData<Resource<ReviewResponse>>()
    val reviewsLiveData: LiveData<Resource<ReviewResponse>> get() = _reviewsLiveData


    lateinit var reviewResponse:ReviewResponse
    fun getCurrentUserId() = LoginUtils.getInstance(getApplication())!!.userInfo()._id




    fun getReviewsByProductId(id:String){
        viewModelScope.launch {
            try {
                _reviewsLiveData.value = Resource.loading(null);
                reviewResponse = ReviewResponse()
                val response = RetrofitHandler.getItemWebService().getReviewsByItemId(id = id)
                if (response.isSuccessful){
                    if (response.body() != null) {

                        reviewResponse = response.body()!!
                        getReviews()

                    }
                    else
                        _reviewsLiveData.value = Resource.error("empty");
                }else{
                    _reviewsLiveData.value = Resource.error(response.code().toString());
                }
            } catch (e: SocketTimeoutException) {
                _reviewsLiveData.value = Resource.error("No Internet Connection")
            } catch (ex: Exception) {
                _reviewsLiveData.value = Resource.error("Error happen while Searching. try again ")
            }

        }
    }

    private fun getReviews(){
        for (i in reviewResponse.indices){
            getUserById(reviewResponse[i].userId,i)
        }
    }

    private fun getUserById(id: String, i: Int){
        viewModelScope.launch {
            try{
            val response = RetrofitHandler.getItemWebService().getUserById(id = id)
            if (response.isSuccessful){
                if (response.body() != null)
                    reviewResponse[i].user = response.body()!!

                 _reviewsLiveData.value = Resource.success(reviewResponse)

            }
        } catch (e: SocketTimeoutException) {
            _reviewsLiveData.value = Resource.error("No Internet Connection")
        } catch (ex: Exception) {
            _reviewsLiveData.value = Resource.error("Error happen. try again ")
        }
        }
    }

    fun getReviewByRate(id: String , rate:Int){
        viewModelScope.launch {
            try{
            _reviewsLiveData.value = Resource.loading(null);
            val response = RetrofitHandler.getItemWebService().getReviewsByRate(id = id,rating = rate)
            if (response.isSuccessful){
                if (response.body() != null) {
                    reviewResponse = response.body()!!
                    getReviews()
                }
                else
                    _reviewsLiveData.value = Resource.error("empty");
            }else{
                _reviewsLiveData.value = Resource.error(response.code().toString());
            }
        } catch (e: SocketTimeoutException) {
            _reviewsLiveData.value = Resource.error("No Internet Connection")
        } catch (ex: Exception) {
            _reviewsLiveData.value = Resource.error("Error happen. try again ")
        }
        }
    }

    fun addReview(reviewRequest: ReviewRequest , productId:String){
        viewModelScope.launch {
            val response = RetrofitHandler.getItemWebService().postReview(reviewRequest)
            if (response.isSuccessful){
                if (response.body() != null) {
                    getReviewsByProductId(productId)
                }
            }else{
            }
        }
    }

}