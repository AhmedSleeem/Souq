package ahmed.adel.sleeem.clowyy.souq.ui.activity.login

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.request.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.LoginResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {
    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> get() = _login

    //, token : String
    fun loginUser(loginRequest: LoginRequest) = viewModelScope.launch {
        try {
            _login.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().loginUser(loginRequest)
            if (response.isSuccessful) {
                if (response.body() != null)
                    _login.value = Resource.success(response.body()!!);
            } else {
                _login.value = Resource.error(response.code().toString())
            }
        } catch (e: SocketTimeoutException) {
            _login.value = Resource.error("Please check internet connection...")
        } catch (ex: Exception) {
            _login.value = Resource.error("Error happen while login")
        }
    }
}