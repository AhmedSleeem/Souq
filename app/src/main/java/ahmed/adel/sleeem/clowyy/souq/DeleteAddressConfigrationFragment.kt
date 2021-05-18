package ahmed.adel.sleeem.clowyy.souq

import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentDeleteAddressConfigrationBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentFilterBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

class DeleteAddressConfigrationFragment : Fragment() {
    private lateinit var binding: FragmentDeleteAddressConfigrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            binding = FragmentDeleteAddressConfigrationBinding.inflate(inflater, container, false)
            val view = binding.root
            return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelAction.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_deleteAddressConfigrationFragment_to_adressFragment);
        }
    }
}