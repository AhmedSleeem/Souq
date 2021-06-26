package ahmed.adel.sleeem.clowyy.souq.ui.activity

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.pojo.request.UserRequist
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.account.ProfileViewModel
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.*

class SplashActivity : AppCompatActivity() {
    private lateinit var userRequist: UserRequist
    private lateinit var viewModel: ProfileViewModel

    //login again

    val ANIMATION_DURATION: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
      //  setApplicationLanguage()
        // HERE WE ARE TAKING THE REFERENCE OF OUR IMAGE
        // SO THAT WE CAN PERFORM ANIMATION USING THAT IMAGE
        val backgroundImage: ImageView = findViewById(R.id.SplashScreenImage)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        backgroundImage.startAnimation(slideAnimation)


        val backgroundText: TextView = findViewById(R.id.title_txt)
        val animation = AnimationUtils.loadAnimation(this, R.anim.buttom_animation)
        backgroundText.startAnimation(animation)

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

//            if(onBoardingFinished()){
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }else{
//
//            }
        }, 5000) // 3000 is the delayed time in milliseconds.

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        userRequist = LoginUtils.getInstance(applicationContext)!!.getUserRequist()
        Log.e("TAGgggggg", userRequist.toString())
        viewModel.updateUserInfo(userRequist)
        updateUser()

    }


    private fun updateUser() {
        viewModel.userInfo.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    val errorMessage =    when(it.message?.toInt()){
                        400  -> "No Internet Connection"
                        else -> "Server Interrupted"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()

                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        Log.e("sssss", it?._id!!)
                        LoginUtils.getInstance(applicationContext)!!.saveUserInfo(it)
                    }
                }
            }
        })
    }

    private fun setApplicationLanguage() {
        val sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        var language = sharedPreferences.getString("langauge", "")
        if (language == "") language = "en"
        val res: Resources = this.resources
        val display: DisplayMetrics = res.getDisplayMetrics()
        val configuration: Configuration = res.getConfiguration()
        configuration.locale = Locale(language)
        res.updateConfiguration(configuration, display)
    }

}