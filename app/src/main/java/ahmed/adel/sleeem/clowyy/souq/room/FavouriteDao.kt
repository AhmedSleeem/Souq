package ahmed.adel.sleeem.clowyy.souq.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item:FavouriteItem)

    @Delete
    suspend fun deleteItem(item:FavouriteItem)

    @Query("SELECT * FROM favourite_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<FavouriteItem>>
}
