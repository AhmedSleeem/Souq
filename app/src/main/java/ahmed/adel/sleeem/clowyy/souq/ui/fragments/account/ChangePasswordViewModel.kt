package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.api.ApiClient
import ahmed.adel.sleeem.clowyy.souq.pojo.PasswordRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.PasswordResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {
    private val _password = MutableLiveData<Resource<PasswordResponse>>()
    val password: LiveData<Resource<PasswordResponse>> get() = _password

    fun updatePassword(passwordRequest: PasswordRequest) = viewModelScope.launch {
        _password.value = Resource.loading(null)
        val response = ApiClient.apiService().updatePassword(passwordRequest)
        if (response.isSuccessful) {
            if (response.body() != null)
                _password.value = Resource.success(response.body()!!);
        } else {
            _password.value = Resource.error(response.errorBody().toString())
        }
    }
}