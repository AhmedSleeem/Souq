package ahmed.adel.sleeem.clowyy.souq.ui.activity.login

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.RegisterRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.RegisterResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _register = MutableLiveData<Resource<RegisterResponse>>()
    val register: LiveData<Resource<RegisterResponse>> get() = _register

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    fun registerUser(registerRequest: RegisterRequest) = viewModelScope.launch {
        _register.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().registerUser(registerRequest)
        if (response.isSuccessful) {
            if (response.body() != null) {
                _token.value = response.headers()["X-Auth-Token"]
                _register.value = Resource.success(response.body()!!)
            }
        } else {
            _register.value = Resource.error(response.errorBody().toString())
        }
    }
}