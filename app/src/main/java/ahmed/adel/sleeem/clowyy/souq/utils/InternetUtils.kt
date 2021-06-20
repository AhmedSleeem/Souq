package ahmed.adel.sleeem.clowyy.souq.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class InternetUtils {
    companion object{
        fun isNetworkConnected(context: Context): Boolean {
            // Get a reference to the ConnectivityManager to check state of network connectivity
            val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // Get details on the currently active default database network
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}