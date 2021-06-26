package ahmed.adel.sleeem.clowyy.souq.ui.activity


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityMainBinding
import ahmed.adel.sleeem.clowyy.souq.notifications.Notifications
import ahmed.adel.sleeem.clowyy.souq.services.MyService
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart.CartViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.DetailsFragment
import ahmed.adel.sleeem.clowyy.souq.utils.OnBadgeChangeListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
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

    private lateinit var notification : Notifications;

    var TAG = "MAINACTIVITY_Socket";
    var onNewMessage = Emitter.Listener { args ->
        runOnUiThread(Runnable {

            val data = args[0] as JSONObject
            val message = data.getString("message");

            notification.createNotificationChannelID(
                resources.getString(R.string.notification_Channel_ID), "push notifications",
                "Item Added In Our System ${message}"
            )

            notification.displayNotification("New Product Added");

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

        val cartView : CartViewModel = ViewModelProvider(this).get(CartViewModel::class.java);
        cartView.cartList.observe(this){
            badge.number = it.size
        }
        DetailsFragment.setOnCountChangeListener = this



        startService(Intent(applicationContext,MyService::class.java));

    }

    private fun isFirstRunning(): Boolean{
        val sharedPref = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    override fun onChange(count: Int) {
        badge.number = count
    }

}