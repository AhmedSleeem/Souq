package ahmed.adel.sleeem.clowyy.souq.room

import ahmed.adel.sleeem.clowyy.souq.room.cart.Cart
import ahmed.adel.sleeem.clowyy.souq.room.cart.CartDAO
import android.content.Context
import androidx.room.*

@Database(entities = [FavouriteItem::class,Cart::class], version = 11 , exportSchema = false)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
    abstract fun cartDao():CartDAO

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
                    "souqDB"
                ).fallbackToDestructiveMigration().build()
                Companion.INSTANCE = instance
                return instance
            }
        }
    }
}

