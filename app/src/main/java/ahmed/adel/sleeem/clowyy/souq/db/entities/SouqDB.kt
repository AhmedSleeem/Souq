package ahmed.adel.sleeem.clowyy.souq.db.entities

import ahmed.adel.sleeem.clowyy.souq.db.entities.dao.CartDAO
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class],version =1 )
abstract class SouqDB:RoomDatabase() {

    abstract val cartDAO : CartDAO


    companion object {

        @Volatile
        private var INSTANCE : SouqDB? = null

        fun getInstance(context:Context):SouqDB{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SouqDB::class.java,
                        "SouqDB"
                    ).build()
                }
                return instance
            }
        }
    }
}