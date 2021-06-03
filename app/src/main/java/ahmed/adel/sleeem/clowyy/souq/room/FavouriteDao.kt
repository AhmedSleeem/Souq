package ahmed.adel.sleeem.clowyy.souq.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: FavouriteItem)

    @Delete
    suspend fun deleteItem(item: FavouriteItem)

//    @Delete
//    suspend fun deleteItemById(item: String)

    @Query("select isInFavourite from in_favourite_table where userId = :id and itemId = :itd ")
    fun selectItem(id: String, itd: String): Boolean

    @Query("SELECT * FROM favourite_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<FavouriteItem>>
}
