package ahmed.adel.sleeem.clowyy.souq.ui.activity

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.ui.activity.login.LoginActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController

class SplashActivity : AppCompatActivity() {

    val ANIMATION_DURATION:Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

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
    }



}