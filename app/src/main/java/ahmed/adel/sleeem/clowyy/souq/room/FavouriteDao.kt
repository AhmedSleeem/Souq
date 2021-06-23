package ahmed.adel.sleeem.clowyy.souq.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: FavouriteItem)

    @Query("delete from favourite_table where userId = :id and itemId = :itd")
    suspend fun deleteItem(id:String,itd:String)

//    @Delete
//    suspend fun deleteItemById(item: String)

    @Query("select userId from favourite_table where userId = :id and itemId = :itd ")
    fun selectItem(id: String, itd: String): String

    @Query("SELECT * FROM favourite_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<FavouriteItem>>
}
