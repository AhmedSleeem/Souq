package ahmed.adel.sleeem.clowyy.souq.ui.activity


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavView.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        //val a ppBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.exploreFragment,R.id.cartFragment,R.id.offerFragment,R.id.profileFragment))
        binding.bottomNavView.setupWithNavController(findNavController(R.id.navHost));

        var badge =binding.bottomNavView.getOrCreateBadge(R.id.cartFragment)
        badge.isVisible = true
        badge.number = 2

    }
}