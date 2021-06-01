package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentAccountBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.ui.activity.login.LoginActivity
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation


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

        // address navigatoin
        binding.logoutLayout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exit")
                .setMessage("Are You sure you want to Logout.")
                .setPositiveButton("Yes") { dialogInterface, which ->
                    LoginUtils.getInstance(requireContext())!!.clearAll()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("No") { dialogInterface, which ->
                    Toast.makeText(requireContext(), "Logout Canceled.", Toast.LENGTH_LONG).show()
                }.show()
        }
    }
}