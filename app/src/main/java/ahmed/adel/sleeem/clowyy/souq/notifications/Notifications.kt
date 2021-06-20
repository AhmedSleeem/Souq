package ahmed.adel.sleeem.clowyy.souq.notifications

import ahmed.adel.sleeem.clowyy.souq.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class Notifications(val context: Context) {



    private var notificationMannger:NotificationManager?  = null

    init {
        notificationMannger =  context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    fun createNotificationChannelID(id:String,name:String,channelDescription:String){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importanceHigh = NotificationManager.IMPORTANCE_HIGH;
            val channel = NotificationChannel(id,name,importanceHigh).apply {
                description = channelDescription;
            }
        notificationMannger?.createNotificationChannel(channel);
        }
    }

    fun displayNotification(contentText:String){
        val notificationID = 33;
        val notification = NotificationCompat.Builder(context,context.resources.getString(R.string.notification_Channel_ID) )
            .setContentTitle("Sell3a")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build();

        notificationMannger?.notify(notificationID,notification);
    }
}