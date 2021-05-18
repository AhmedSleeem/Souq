package ahmed.adel.sleeem.clowyy.souq.ui.fragments.address

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentShipToBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.AddressItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController


class ShipToFragment : Fragment(),View.OnClickListener {
    private lateinit var addressAdapter: AddressAdapter
    private var _binding: FragmentShipToBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShipToBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        initShipToRecyclerView()
        binding.nextShipToButton.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when (v) {
            binding.nextShipToButton ->{
                val action = ShipToFragmentDirections.actionShipToFragmentToSuccessFragment()
                view?.findNavController()?.navigate(action)
            }
        }
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