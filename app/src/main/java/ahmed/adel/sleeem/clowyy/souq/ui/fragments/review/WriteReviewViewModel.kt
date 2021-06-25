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

class WriteReviewViewModel(application: Application) : AndroidViewModel(application) {

    private var _addReviewLiveData = MutableLiveData<Resource<ReviewResponse.Item>>()
    val addReviewLiveData: LiveData<Resource<ReviewResponse.Item>> get() = _addReviewLiveData

    private var _modifyReviewLiveData = MutableLiveData<Resource<ReviewResponse.Item>>()
    val modifyReviewLiveData: LiveData<Resource<ReviewResponse.Item>> get() = _modifyReviewLiveData

    private var _deleteReviewLiveData = MutableLiveData<Resource<DeleteReviewResponse>>()
    val deleteReviewLiveData: LiveData<Resource<DeleteReviewResponse>> get() = _deleteReviewLiveData


    fun addReview(reviewRequest: ReviewRequest) {
        viewModelScope.launch {
            try {
                _addReviewLiveData.value = Resource.loading(null)
                val response = RetrofitHandler.getItemWebService().postReview(reviewRequest)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _addReviewLiveData.value = Resource.success(response.body()!!)
                    } else
                        _addReviewLiveData.value = Resource.error("empty");
                } else {
                    _addReviewLiveData.value = Resource.error(response.code().toString());
                }
            } catch (e: SocketTimeoutException) {
                _addReviewLiveData.value = Resource.error("No Internet Connection")
            } catch (ex: Exception) {
                _addReviewLiveData.value = Resource.error("Error happen. try again ")
            }
        }
    }

    fun modifyReview(modifyReviewRequest: ModifyReviewRequest) {

        viewModelScope.launch {
            try {
                _modifyReviewLiveData.value = Resource.loading(null)
                val response = RetrofitHandler.getItemWebService().modifyReview(modifyReviewRequest)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _modifyReviewLiveData.value = Resource.success(response.body()!!)
                    } else
                        _modifyReviewLiveData.value = Resource.error("empty");
                } else {
                    _modifyReviewLiveData.value = Resource.error(response.code().toString());
                }
            } catch (e: SocketTimeoutException) {
                _modifyReviewLiveData.value = Resource.error("No Internet Connection")
            } catch (ex: Exception) {
                _modifyReviewLiveData.value = Resource.error("Error happen. try again ")
            }
        }
    }

    fun deleteReview(deleteReviewRequest: DeleteReviewRequest) {
        viewModelScope.launch {
            try {
                _deleteReviewLiveData.value = Resource.loading(null)
                val response = RetrofitHandler.getItemWebService().deleteReview(deleteReviewRequest)
                if (response.isSuccessful)
                    _deleteReviewLiveData.value = Resource.success(response.body()!!)
                else
                    _deleteReviewLiveData.value = Resource.error(response.code().toString());
            } catch (e: SocketTimeoutException) {
                _deleteReviewLiveData.value = Resource.error("No Internet Connection")
            } catch (ex: Exception) {
                _deleteReviewLiveData.value = Resource.error("Error happen. try again ")
            }
        }
    }
}