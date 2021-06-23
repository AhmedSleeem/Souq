package ahmed.adel.sleeem.clowyy.souq.room

import androidx.lifecycle.LiveData

class FavouriteRepository(private val favouriteDao: FavouriteDao) {

    val readAllData: LiveData<List<FavouriteItem>> = favouriteDao.readAllData()

    suspend fun addItem(item: FavouriteItem) {
        favouriteDao.addItem(item)
    }

    suspend fun deleteItem(id:String,itd:String) {
        favouriteDao.deleteItem(id,itd)
    }

    suspend fun selectItem(id: String,itd:String) : Boolean {
       var select =  favouriteDao.selectItem(id,itd) != null ;

        select = select and !select.equals("");
        return select
    }

//    suspend fun deleteItemById(item: String) {
//        favouriteDao.deleteItemById(item)
//    }

}
