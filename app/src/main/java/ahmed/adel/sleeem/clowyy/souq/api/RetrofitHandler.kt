package ahmed.adel.sleeem.clowyy.souq.api

import ahmed.adel.sleeem.clowyy.souq.models.ApiManager
import ahmed.adel.sleeem.clowyy.souq.models.network.UserApiService
import ahmed.adel.sleeem.clowyy.souq.utils.Constants.BASE_API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHandler {

    private var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .callTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(4, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_API)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

    fun getItemWebService():ItemWebServices{
       return retrofit.create(ItemWebServices::class.java)
    }
}