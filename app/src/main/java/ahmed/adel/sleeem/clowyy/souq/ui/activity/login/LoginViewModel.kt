package ahmed.adel.sleeem.clowyy.souq.ui.activity.login

import ahmed.adel.sleeem.clowyy.souq.api.ApiClient
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> get() = _login
//, token : String
    fun loginUser(loginRequest: LoginRequest) = viewModelScope.launch {
        _login.value = Resource.loading(null)
        val response = ApiClient.apiService().loginUser(loginRequest)
        if (response.isSuccessful) {
            if (response.body() != null)
                _login.value = Resource.success(response.body()!!);
        } else {
            _login.value = Resource.error(response.errorBody().toString())
        }
    }
}