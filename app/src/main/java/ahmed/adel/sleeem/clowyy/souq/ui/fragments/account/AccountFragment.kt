package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentAccountBinding
import ahmed.adel.sleeem.clowyy.souq.ui.activity.MainActivity
import ahmed.adel.sleeem.clowyy.souq.ui.activity.login.LoginActivity
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // profile navigatoin
        binding.profileLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_accountFragment_to_profileFragment2);
        }

        // order navigatoin
        binding.orderLayout.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_accountFragment_to_orderFragment);
        }

        // order navigatoin
        binding.languageLayout.setOnClickListener {

            showPopupMenu()
        }

        // address navigatoin
        binding.logoutLayout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exit")
                .setMessage("Are You sure you want to Logout.")
                .setPositiveButton("Yes") { dialogInterface, which ->
                    FirebaseAuth.getInstance().signOut()
                    LoginUtils.getInstance(requireContext())!!.clearAll()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("No") { dialogInterface, which ->
                    Toast.makeText(requireContext(), "Logout Canceled.", Toast.LENGTH_LONG).show()
                }.show()
        }
    }
    private fun showPopupMenu() {
      //  binding.languageLayout.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.languageLayout)
            popupMenu.menuInflater.inflate(R.menu.language_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.arabic -> {

                        setApplicationLanguage("ar")

                        val res = context?.getResources();

                        val config = res?.getConfiguration();
                        config?.locale = Locale("ar")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            config?.setLayoutDirection(Locale("ar"));
                        }
                    }
                    R.id.english -> {
                        setApplicationLanguage("en")
                        val res = context?.getResources();

                        val config = res?.getConfiguration();
                        config?.locale = Locale("en")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            config?.setLayoutDirection(Locale("en"));
                        }
                    }
                }
                true
            })
            popupMenu.show()
      //  }
    }

    private fun setApplicationLanguage(language: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("langauge", language)
        editor.apply()
        val res: Resources = requireActivity().resources
        val display: DisplayMetrics = res.getDisplayMetrics()
        val configuration: Configuration = res.getConfiguration()
        configuration.locale = Locale(language)
        res.updateConfiguration(configuration, display)
        val refresh = Intent(activity, MainActivity::class.java)
        refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        requireActivity().startActivity(refresh)
    }
}