package ahmed.adel.sleeem.clowyy.souq.ui.activity


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityMainBinding
import ahmed.adel.sleeem.clowyy.souq.notifications.Notifications
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.DetailsFragment
import ahmed.adel.sleeem.clowyy.souq.utils.OnBadgeChangeListener
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.graphics.BitmapCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class MainActivity : AppCompatActivity() , OnBadgeChangeListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var badge : BadgeDrawable

    private lateinit var mySocket: Socket;

    private var notificationMannger: NotificationManager?  = null

    private lateinit var notification : Notifications;

    val TAG = "MAINACTIVITY_Socket";

    var onNewMessage = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            Log.i(TAG, ":new Product Added ")
            val data = args[0] as JSONObject

            notification.createNotificationChannelID(
                resources.getString(R.string.notification_Channel_ID),
                "push_Notification",
                "new Post is Added"
            )



            val message = data.getString("message");
            notification.displayNotification(message);

        })
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavView.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        //val a ppBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.exploreFragment,R.id.cartFragment,R.id.offerFragment,R.id.profileFragment))
        binding.bottomNavView.setupWithNavController(findNavController(R.id.navHost));

        badge =binding.bottomNavView.getOrCreateBadge(R.id.cartFragment)
        badge.isVisible = true
        badge.number = 0




        DetailsFragment.setOnCountChangeListener = this

        if(!isFirstRunning()) {
            this.findNavController(R.id.navHost)
                .navigate(R.id.action_homeFragment_to_viewPagerFragment)
        }


        // Initialized notification class
        notification = Notifications(this);

        notificationMannger =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



        // Intialized Socket IO
        mySocket = IO.socket("https://souqitigraduationproj.herokuapp.com");

        mySocket.open()

        mySocket.on("newProductAdded", onNewMessage);

        mySocket.on(Socket.EVENT_CONNECT, Emitter.Listener {

            Log.i(TAG, "onCreate: connected");

        });


    }

    private fun isFirstRunning(): Boolean{
        val sharedPref = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    override fun onChange(count: Int) {
        badge.number = count
    }

    fun createNotificationChannelID(id:String,name:String,channelDescription:String){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val importanceHigh = NotificationManager.IMPORTANCE_HIGH;
            val channel = NotificationChannel(id,name,importanceHigh).apply {
                description = channelDescription;
            }
            notificationMannger?.createNotificationChannel(channel);
        }
    }

    fun displayNotification(contentText:String){
        val notificationID = 33;
        val notification = NotificationCompat.Builder(this,resources.getString(R.string.notification_Channel_ID) )
            .setContentTitle("Sell3a")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.btn_plus)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build();

        notificationMannger?.notify(notificationID,notification);
    }


}