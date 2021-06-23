package ahmed.adel.sleeem.clowyy.souq.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    fun deleteItem(id:String,itd:String)  {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(id,itd)
        }
    }

    suspend fun selectItem(id: String, itd: String): Boolean {
         var isIn: Boolean = false
       var isInIs =  viewModelScope.async (Dispatchers.IO) {
              repository.selectItem(id, itd)
        }
//        isInIs.start()
        return isInIs.await()
    }

//    fun deleteItemById(item: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteItemById(item)
//        }
//    }
}
