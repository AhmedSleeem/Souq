package ahmed.adel.sleeem.clowyy.souq.ui.activity


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityMainBinding
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.DetailsFragment
import ahmed.adel.sleeem.clowyy.souq.utils.OnBadgeChangeListener
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class MainActivity : AppCompatActivity() , OnBadgeChangeListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var badge : BadgeDrawable

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

//        DetailsFragment.setOnCountChangeListener = this

        if(!isFirstRunning()) {
            this.findNavController(R.id.navHost)
                .navigate(R.id.action_homeFragment_to_viewPagerFragment)
        }
    }

    private fun isFirstRunning(): Boolean{
        val sharedPref = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    override fun onChange(count: Int) {
        badge.number = count
    }


}