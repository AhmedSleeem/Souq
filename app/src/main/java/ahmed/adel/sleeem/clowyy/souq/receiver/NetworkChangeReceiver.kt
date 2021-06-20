package ahmed.adel.sleeem.clowyy.souq.receiver

import ahmed.adel.sleeem.clowyy.souq.utils.InternetUtils.Companion.isNetworkConnected
import ahmed.adel.sleeem.clowyy.souq.utils.OnNetworkListener
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkChangeReceiver : BroadcastReceiver() {
    private lateinit var onNetworkListener: OnNetworkListener
    fun setOnNetworkListener(onNetworkListener: OnNetworkListener) {
        this.onNetworkListener = onNetworkListener
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (!isNetworkConnected(context)) {
            onNetworkListener.onNetworkDisconnected()
        } else {
            onNetworkListener.onNetworkConnected()
        }
    }
}