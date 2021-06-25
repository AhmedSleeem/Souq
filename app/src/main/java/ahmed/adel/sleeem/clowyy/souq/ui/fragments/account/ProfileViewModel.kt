package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.request.UserRequist
import ahmed.adel.sleeem.clowyy.souq.pojo.response.UserResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.SocketTimeoutException

class ProfileViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<Resource<UserResponse>>()
    val userInfo: LiveData<Resource<UserResponse>> get() = _userInfo

    fun updateUserInfo(userRequist: UserRequist) = viewModelScope.launch {
        try {
            _userInfo.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().updateAccount(userRequist)
            if (response.isSuccessful) {
                if (response.body() != null)
                    _userInfo.value = Resource.success(response.body()!!);
            } else {
                _userInfo.value = Resource.error(response.code().toString())
            }
        }catch (e:SocketTimeoutException){
            _userInfo.value = Resource.error("Please check internet connection...")
        }catch (ex:Exception){
            _userInfo.value = Resource.error("Error happen when update user data")
        }

    }
}