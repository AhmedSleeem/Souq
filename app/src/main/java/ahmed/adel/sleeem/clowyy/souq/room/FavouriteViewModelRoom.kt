package ahmed.adel.sleeem.clowyy.souq.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModelRoom(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<FavouriteItem>>
    private val repository: FavouriteRepository

    init {
        val itemDao = FavouriteDatabase.getDatabase(application).favouriteDao()
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

    fun selectItem(id: String, itd: String): Boolean {
        var isIn: Boolean = false
        viewModelScope.launch(Dispatchers.IO) {
            isIn = repository.selectItem(id, itd)
        }
        return isIn
    }

//    fun deleteItemById(item: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteItemById(item)
//        }
//    }
}
