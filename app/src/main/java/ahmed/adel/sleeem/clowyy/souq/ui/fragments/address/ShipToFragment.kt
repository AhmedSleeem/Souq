package ahmed.adel.sleeem.clowyy.souq.ui.fragments.address

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentShipToBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.AddressItem
import ahmed.adel.sleeem.clowyy.souq.pojo.Cupone
import ahmed.adel.sleeem.clowyy.souq.pojo.request.OrderRequest
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart.CartViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.DetailsFragment
import ahmed.adel.sleeem.clowyy.souq.utils.CartRoom
import ahmed.adel.sleeem.clowyy.souq.utils.CuponeUtils
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs


class ShipToFragment : Fragment(),View.OnClickListener {
    private lateinit var addressAdapter: AddressAdapter
    private var _binding: FragmentShipToBinding? = null

    var address: String? = null
    var country: String = ""
    var street: String = ""
    var city: String = ""
    var state: String = ""
    var zipCode: String = ""
    val args : ShipToFragmentArgs by navArgs()
    private lateinit var viewModel : CartViewModel
    private lateinit var orderRequest : OrderRequest

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShipToBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderRequest = args.orderRequest
        viewModel.addNewOrder(orderRequest =orderRequest)
        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        binding.editAddressButton.setOnClickListener {
            val action = ShipToFragmentDirections.actionShipToFragmentToAddAddressFragment("edit")
            it.findNavController().navigate(action)
        }

        binding.nextBtn.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when (v) {
            binding.nextBtn -> {
                subscribeToLiveData()
                if (LoginUtils.getInstance(requireContext())!!.userInfo().Address != null) {
                    orderRequest.Address =
                        LoginUtils.getInstance(requireContext())!!.userInfo().Address!!
                    viewModel.addNewOrder(orderRequest = orderRequest)

                    Log.i( "onClick: ",orderRequest.totalPrice.toString());
                    val action =
                        ShipToFragmentDirections.actionShipToFragmentToPaymentFragment(orderRequest.totalPrice.toString())

                    view?.findNavController()?.navigate(action)
                    CartRoom.cartList.clear()
                    var cupone = Cupone()
                    CuponeUtils(requireContext()).editCupone(cupone)
                    DetailsFragment.badgeCount = 0
                    DetailsFragment.setOnCountChangeListener?.onChange(DetailsFragment.badgeCount)
                } else {
                    Toast.makeText(requireContext(), "Enter your Address", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

    fun subscribeToLiveData(){
        viewModel.addOrderLiveData.observe(viewLifecycleOwner , Observer {
            when(it.status){
                Resource.Status.LOADING ->{

                }
                Resource.Status.SUCCESS ->{
                    Log.e("ssss", it.status.toString() )
                }
            }
        })
    }


    override fun onResume() {
        super.onResume()
        address = LoginUtils.getInstance(requireContext())!!.userInfo().Address
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