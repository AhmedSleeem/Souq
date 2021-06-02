package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentCartBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.request.OrderRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.OrderResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.utils.CartRoom
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class CartFragment : Fragment(),View.OnClickListener {
    private lateinit var adapter: CartAdapter
    private lateinit var viewModel: CartViewModel
    private var _binding: FragmentCartBinding? = null
    private lateinit var  orderRequest : OrderRequest
    private lateinit var  orderRequestItemId : OrderRequest.ItemId
    val  orderRequestItemIdsList = mutableListOf<OrderRequest.ItemId>()

    private val binding get() = _binding!!
    val quotes = arrayOf("order id" , )
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartItems()

        // orderRequest
        val date = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
        orderRequest = OrderRequest(itemIds = orderRequestItemIdsList)
        orderRequestItemId = OrderRequest.ItemId()
        if (CartRoom.cartList != null) {
            orderRequest.orderCode = getRandomString(10)
            for (item in CartRoom.cartList) {
                orderRequestItemId.id = item.id.toString()
                orderRequestItemId.color = item.selectedColor.toString()
                orderRequestItemId.count = item.countOfSelectedItem
                orderRequestItemId.size = item.selectedSize.toString()
                orderRequestItemIdsList.add(orderRequestItemId)
            }
            orderRequest.itemIds = orderRequestItemIdsList
            orderRequest.userId = LoginUtils.getInstance(requireContext())!!.userInfo()._id
            orderRequest.Address = LoginUtils.getInstance(requireContext())!!.userInfo().Address
            orderRequest.orderDate = date
            Log.e("sss object", orderRequest.orderCode!!)
            viewModel.addNewOrder(orderRequest)
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
                        calculateTotalPrice()
                    }
                }
            }
        }

        adapter.setOnCountClickListner = object : CartAdapter.CountClickListner {
            override fun onClick() {
                calculateTotalPrice()
            }

        }

        //listener --> fragment
        //listener.onClick
        // count




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
                    adapter.changeData(it.data!!,true)
                }
            }
        })

    }








    override fun onClick(v: View) {
        when (v) {
            binding.checkOutButton -> {
                val action = CartFragmentDirections.actionCartFragmentToShipToFragment()
                view?.findNavController()?.navigate(action)
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

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}