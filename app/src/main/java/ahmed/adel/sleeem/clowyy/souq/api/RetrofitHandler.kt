package ahmed.adel.sleeem.clowyy.souq.api

import ahmed.adel.sleeem.clowyy.souq.utils.Constants.BASE_API
import android.content.Context
import com.google.android.gms.common.api.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHandler{

    private var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
   // private val networkInterceptor = NetworkStateInterceptor(context)



    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
       // .addInterceptor(networkInterceptor)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_API)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun getItemWebService(): ItemWebServices {
        return retrofit.create(ItemWebServices::class.java)
    }


}
