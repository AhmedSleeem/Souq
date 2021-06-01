package ahmed.adel.sleeem.clowyy.souq.ui.fragments.address

import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentDeleteAddressConfigrationBinding
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
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
            LoginUtils.getInstance(requireContext())!!.updateAddress("N/F")
            Toast.makeText(requireContext(), "Address Deleted Successfully", Toast.LENGTH_LONG)
                .show()
            Navigation.findNavController(it).navigateUp()
        }
    }
}