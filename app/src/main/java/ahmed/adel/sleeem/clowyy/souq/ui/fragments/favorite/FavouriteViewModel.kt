package ahmed.adel.sleeem.clowyy.souq.ui.fragments.favorite

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavouriteViewModel: ViewModel() {

    private val _itemFav = MutableLiveData<Resource<ItemResponse>>()
    val itemFav: MutableLiveData<Resource<ItemResponse>> get() = _itemFav


    fun getItemsById(id: String) = viewModelScope.launch {
        try {
            _itemFav.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsById(id)
            if (response.isSuccessful) {
                if (response.body() != null)
                    _itemFav.value = Resource.success(response.body()!!)
            } else {
                _itemFav.value = Resource.error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            _itemFav.value = Resource.error(e.message.toString())
        }

    }

}