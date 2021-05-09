package ahmed.adel.sleeem.clowyy.souq

import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentAddAddressBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentChooseGenderBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation

class AddAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentAddAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        val view = binding.root

        val tybes = resources.getStringArray(R.array.country_list)
        var arrayAdapter = ArrayAdapter(requireContext() ,
            R.layout.gender_dropdown_item, tybes)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        // add address navigation
        binding.addAddressBtn.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_addAddressFragment_to_deleteAddressConfigrationFragment);

        }
    }

}