package ahmed.adel.sleeem.clowyy.souq.services

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.notifications.Notifications
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.util.*

class MyService : Service() {

    private lateinit var mySocket: Socket;

    private lateinit var notification : Notifications;


    var TAG = "MAINACTIVITY_Socket";
    var onNewMessage = Emitter.Listener { args ->


        run {

            val data = args[0] as JSONObject
            val message = data.getString("message");

            notification.createNotificationChannelID(resources.getString(R.string.notification_Channel_ID)
                ,"push notifications",
                "Item Added In Our System ${message}")

            notification.displayNotification("New Product Added");

        };
    }

    var onShipStatusChange = Emitter.Listener { args ->


        run {

            val data = args[0] as JSONObject
            val email = data.getString("email");
            val message = data.getString("message");

            Log.i(TAG, "onShipStatusChange: ");

            val user = LoginUtils.getInstance(applicationContext)!!.userInfo()


            if(user.email == email) {

                notification.createNotificationChannelID(
                    resources.getString(R.string.notification_Channel_ID), "push notifications",
                    "${message}"
                )

                notification.displayNotification("Your Order getting in progress");
            }

        };
    }

    override fun onCreate() {
        super.onCreate()


        //Notification
        notification = Notifications(this);


        //Socet Io
        mySocket = IO.socket("https://souqitigraduationproj.herokuapp.com");

        mySocket.open()

//        mySocket.on("newProductAdded", onNewMessage);
//
//        mySocket.on("onShipStatusChange", onShipStatusChange);


        mySocket.on("newProductAdded", onNewMessage);

        mySocket.on("onShipStatusChange", onShipStatusChange);


        mySocket.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.i(TAG, "onCreate: connected");
        });

    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()

        mySocket.disconnect();
        mySocket.off("newProductAdded",onNewMessage);
        mySocket.off("onShipStatusChange",onShipStatusChange);
    }


}