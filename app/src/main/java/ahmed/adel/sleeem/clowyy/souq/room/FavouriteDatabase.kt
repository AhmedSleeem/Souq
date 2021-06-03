package ahmed.adel.sleeem.clowyy.souq.room

import android.content.Context
import androidx.room.*

@Database(entities = [FavouriteItem::class,IsInFavourite::class], version = 5 , exportSchema = false)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteDatabase? = null

        fun getDatabase(context: Context): FavouriteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDatabase::class.java,
                    "favourite_database"
                ).fallbackToDestructiveMigration().build()
                Companion.INSTANCE = instance
                return instance
            }
        }
    }
}

