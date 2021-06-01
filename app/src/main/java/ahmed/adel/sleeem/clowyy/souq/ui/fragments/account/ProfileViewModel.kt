package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.api.ApiClient
import ahmed.adel.sleeem.clowyy.souq.pojo.UserRequist
import ahmed.adel.sleeem.clowyy.souq.pojo.UserResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<Resource<UserResponse>>()
    val userInfo: LiveData<Resource<UserResponse>> get() = _userInfo

    fun updateUserInfo(userRequist: UserRequist) = viewModelScope.launch {
        _userInfo.value = Resource.loading(null)
        val response = ApiClient.apiService().updateAccount(userRequist)
        if (response.isSuccessful) {
            if (response.body() != null)
                _userInfo.value = Resource.success(response.body()!!);
        } else {
            _userInfo.value = Resource.error(response.errorBody().toString())
        }
    }
}