package ahmed.adel.sleeem.clowyy.souq.ui.fragments.address

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentAddressBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.AddressItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

class AddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentAddressBinding
    private lateinit var addressAdapter: AddressAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        val view = binding.root
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
            Navigation.findNavController(it).navigate(R.id.action_adressFragment_to_addAddressFragment);

        }

        initShipToRecyclerView()
    }

    private fun initShipToRecyclerView() {
        addressAdapter = AddressAdapter { _, _, _ -> }

        var item1 = AddressItem()
        var item3 = AddressItem()

        var starList = mutableListOf<AddressItem>(item1,item3)
        addressAdapter.swapData(starList)
        binding.shipToRecyclerView.apply {
            adapter = addressAdapter
        }
    }
}