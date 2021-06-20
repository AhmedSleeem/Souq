package ahmed.adel.sleeem.clowyy.souq.ui.fragments.address

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentAddressBinding
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private lateinit var addressAdapter: AddressAdapter

    lateinit var address: String
    var country: String = ""
    var street: String = ""
    var city: String = ""
    var state: String = ""
    var zipCode: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        binding.editAddressButton.setOnClickListener {
            val action = AddressFragmentDirections.actionAdressFragmentToAddAddressFragment("edit")
            it.findNavController().navigate(action)
        }
        binding.deleteAddressImageView.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_adressFragment_to_deleteAddressConfigrationFragment)
        }
        // add address navigation
        binding.addAddressBtn.setOnClickListener {
            val action = AddressFragmentDirections.actionAdressFragmentToAddAddressFragment("add")
            it.findNavController().navigate(action)

        }

        //initShipToRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        address = LoginUtils.getInstance(requireContext())!!.userInfo().Address?:"N/F"
        filterAddress(address)
    }

    private fun filterAddress(address: String?) {
        Log.e("TAG", "filterAddress: " + address)
        if (address == "N/F" || address.isNullOrEmpty()) {
            binding.cityTextView.text = " Address not founded"
            binding.descAddresstextView.text = "N/F"
        } else {
            val list = address.split(",").toTypedArray()
            Log.e("TAG", "filterAddress: " + list)
            country = list[0]
            city = list[1]
            street = list[2]
            state = list[3]
            zipCode = list[4]
            binding.cityTextView.text = country
            binding.descAddresstextView.text = zipCode + "," + street + "," + state + "," + city
        }
    }
}

/* private fun initShipToRecyclerView() {
     addressAdapter = AddressAdapter { _, _, _ -> }

     var item1 = AddressItem()
     var item3 = AddressItem()

     var starList = mutableListOf<AddressItem>(item1,item3)
     addressAdapter.swapData(starList)
     binding.shipToRecyclerView.apply {
         adapter = addressAdapter
     }
 }*/
