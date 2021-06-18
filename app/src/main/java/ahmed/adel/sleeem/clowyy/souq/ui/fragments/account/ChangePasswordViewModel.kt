package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.request.PasswordRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.PasswordResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.SocketTimeoutException

class ChangePasswordViewModel : ViewModel() {
    private val _password = MutableLiveData<Resource<PasswordResponse>>()
    val password: LiveData<Resource<PasswordResponse>> get() = _password

    fun updatePassword(passwordRequest: PasswordRequest) = viewModelScope.launch {
        try {
            _password.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().updatePassword(passwordRequest)
            if (response.isSuccessful) {
                if (response.body() != null)
                    _password.value = Resource.success(response.body()!!);
            } else {
                _password.value = Resource.error(response.code().toString())
            }
        }catch (e:SocketTimeoutException){
            _password.value = Resource.error("Please check internet connection...")
        }catch (ex:Exception){
            _password.value = Resource.error("Error happen when update password")
        }

    }
}