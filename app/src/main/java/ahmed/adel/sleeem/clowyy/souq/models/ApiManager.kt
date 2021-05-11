package ahmed.adel.sleeem.clowyy.souq.models

import ahmed.adel.sleeem.clowyy.souq.models.network.UserApiService
import ahmed.adel.sleeem.clowyy.souq.utils.Constants.BASE_API
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager {
    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .callTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(4, TimeUnit.SECONDS)
                .build()


            Retrofit.Builder()
                .baseUrl(BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val apiService by lazy {
            retrofit.create(UserApiService::class.java)
        }
    }
}