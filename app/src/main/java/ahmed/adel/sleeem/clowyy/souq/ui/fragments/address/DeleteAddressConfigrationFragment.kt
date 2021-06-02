package ahmed.adel.sleeem.clowyy.souq.ui.fragments.address

import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentDeleteAddressConfigrationBinding
import ahmed.adel.sleeem.clowyy.souq.utils.Constants
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class DeleteAddressConfigrationFragment : Fragment() {
    private lateinit var binding: FragmentDeleteAddressConfigrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDeleteAddressConfigrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelAction.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        binding.deleteAction.setOnClickListener {
            updateAddress("N/F")
            Toast.makeText(requireContext(), "Address Deleted Successfully", Toast.LENGTH_LONG)
                .show()
            Navigation.findNavController(it).navigateUp()
        }
    }
    fun updateAddress(address: String) {
        val sharedPreferences = requireContext().getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Address", address)
        editor.apply()
    }
}