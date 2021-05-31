package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentCartBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemCartBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.CartItem
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.utils.CartRoom
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class CartFragment : Fragment(),View.OnClickListener {
    private lateinit var adapter: CartAdapter
    private lateinit var viewModel: CartViewModel
    private var _binding: FragmentCartBinding? = null
    private  var acceptFlag : Int = -1

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        adapter = CartAdapter(requireContext())
        binding.cartRecyclerView.adapter = adapter


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartItems()
        for (itwm in CartRoom.cartList) {
            viewModel.getItemsBytitle(itwm.title)
        }

        subscribeToLiveData()
        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        // item clickListners

        adapter.setOnItemClickListner = object : CartAdapter.ItemClickListener {
            override fun onClick(view: View, item: ProductResponse.Item, position: Int) {
                when (view) {
                    adapter.viewBinding.deleteItemBtn -> {
                        CartRoom.cartList.remove(item)
                        adapter.notifyItemRemoved(position)
                    }
                }
            }

        }



        calculateTotalPrice()

        binding.checkOutButton.setOnClickListener(this)
    }


    fun subscribeToLiveData() {
        viewModel.cartItemsLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    Log.e("ssss", "list : " + it.data)
                    adapter.changeData(it.data!!)
                }
            }
        })

    }

    fun liveDataWithApi(){
        viewModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    checkItemsQuantity(it.data!!.get(0))
                }
            }
        })

    }


    override fun onClick(v: View) {
        when (v) {
            binding.checkOutButton -> {
                liveDataWithApi()
                if(acceptFlag == 1) {
                    val action = CartFragmentDirections.actionCartFragmentToShipToFragment()
                    view?.findNavController()?.navigate(action)
                    acceptFlag = -1
                }else if(acceptFlag == 0){
                    Toast.makeText(requireContext(), "Please check item count", Toast.LENGTH_SHORT)
                    acceptFlag = -1
                }
            }
        }
    }

    fun checkItemsQuantity(item: ProductResponse.Item) {
        for (data in CartRoom.cartList) {
            if (item.quantity < data.quantity) {
                Toast.makeText(requireContext(), "Please check item count", Toast.LENGTH_SHORT)
                    .show()
                acceptFlag = 0
            } else {
                Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
                acceptFlag = 1
            }
        }
    }


    fun calculateTotalPrice() {
        var itemCount = 0
        var price = 0.0f

        for (item in CartRoom.cartList) {
            itemCount += item.countOfSelectedItem
            price = (price + (item.countOfSelectedItem * item.price)).toFloat()
        }
        binding.totalItemsCount.text = "Items (" + itemCount.toString() + ")"
        binding.totalItemsPrice.text = price.toString() + " Egp"
        binding.totalPrice.text = price.toString() + " Egp"


    }
}