package ahmed.adel.sleeem.clowyy.souq.room

import androidx.lifecycle.LiveData

class FavouriteRepository(private val favouriteDao: FavouriteDao) {

    val readAllData: LiveData<List<FavouriteItem>> = favouriteDao.readAllData()

    suspend fun addItem(item: FavouriteItem) {
        favouriteDao.addItem(item)
    }

    suspend fun deleteItem(item: FavouriteItem) {
        favouriteDao.deleteItem(item)
    }

    suspend fun selectItem(id: String,itd:String) : Boolean {
       return favouriteDao.selectItem(id,itd)
    }

//    suspend fun deleteItemById(item: String) {
//        favouriteDao.deleteItemById(item)
//    }

}
