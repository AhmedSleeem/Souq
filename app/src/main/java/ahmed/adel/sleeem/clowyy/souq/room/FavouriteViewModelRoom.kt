package ahmed.adel.sleeem.clowyy.souq.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModelRoom (application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<FavouriteItem>>
    private val repository: FavouriteRepository

    init {
        val itemDao = FavouriteDatabase.getDatabse(application).favouriteDao()
        repository = FavouriteRepository(itemDao)
        readAllData = repository.readAllData
    }


    fun addItem(item: FavouriteItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }

    fun deleteItem(item: FavouriteItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(item)
        }
    }

}
