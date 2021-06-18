package ahmed.adel.sleeem.clowyy.souq.api

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkStateInterceptor(val context: Context):Interceptor {
    val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoInternetException("check your network connection!!")
        return chain.proceed(chain.request())
    }

    fun isOnline():Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also{
            return it!=null && it.isConnected
        }
    }
}

class NoInternetException(message :String):IOException(message)