package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentCartBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.Cupone
import ahmed.adel.sleeem.clowyy.souq.pojo.request.OrderRequest
import ahmed.adel.sleeem.clowyy.souq.room.cart.Cart
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.DetailsFragment
import ahmed.adel.sleeem.clowyy.souq.utils.CartRoom
import ahmed.adel.sleeem.clowyy.souq.utils.CuponeUtils
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.app.Activity
import android.content.Context
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


class CartFragment : Fragment(),View.OnClickListener {
    private lateinit var adapter: CartAdapter
    private lateinit var viewModel: CartViewModel
    private var _binding: FragmentCartBinding? = null
    private lateinit var orderRequest: OrderRequest
    private lateinit var orderRequestItemId: OrderRequest.ItemId
    val orderRequestItemIdsList = mutableListOf<OrderRequest.ItemId>()
    lateinit var currentCupone: Cupone
    var totalPrice = 0.0f
    private var cupone: String? = null
    private var cuponeValue: Int = 0
    private lateinit var itemsStr :String
    private lateinit var cartViewModel: ahmed.adel.sleeem.clowyy.souq.room.cart.CartViewModel
private lateinit var uId : String
    private val binding get() = _binding!!
    val quotes = arrayOf("order id")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemsStr =context.resources.getString(R.string.items)
        uId =LoginUtils.getInstance(requireActivity())!!.userInfo()._id!!
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        adapter = CartAdapter(requireContext())
        binding.cartRecyclerView.adapter = adapter
        //binding.cuponeEt.setText(Cupone().cuponeCode)


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (isAdded) {
        currentCupone = CuponeUtils(requireContext()).getCupone() ?: Cupone()
        viewModel.getCartItems()


        cartViewModel =
            ViewModelProvider(this).get(ahmed.adel.sleeem.clowyy.souq.room.cart.CartViewModel::class.java);
        // orderRequest
        val date = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())

        orderRequest = OrderRequest(itemIds = orderRequestItemIdsList)
        orderRequestItemId = OrderRequest.ItemId()

        cartViewModel.userFavorites.observe(requireActivity()) {

            orderRequest.orderCode = getRandomString(10)
            for (item in it) {
                orderRequestItemId.id = item.id.toString()
                orderRequestItemId.companyName = item.companyName
                orderRequestItemId.color = item.color
                orderRequestItemId.count = item.count
                orderRequestItemId.size = item.size
                orderRequestItemIdsList.add(orderRequestItemId)
            }
            orderRequest.itemIds = orderRequestItemIdsList
            orderRequest.userId = uId
            orderRequest.orderDate = date
            orderRequest.totalPrice = totalPrice.toDouble()

        }


//


        subscribeToLiveData()
        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        // item clickListners

        adapter.setOnItemClickListner = object : CartAdapter.ItemClickListener {
            override fun onClick(view: View, item: Cart, position: Int) {


//                when (view) {

//                        CartRoom.cartList.remove(item)
                Log.i("onClick:", " delete cart");
                val user = LoginUtils.getInstance(requireContext())!!.userInfo()
                cartViewModel.delete(user._id!!, item.itemId.toString());

                DetailsFragment.badgeCount--
                DetailsFragment.setOnCountChangeListener?.onChange(DetailsFragment.badgeCount)
                calculateTotalPrice()
                adapter.notifyItemRemoved(position)

//                }
            }
        }

        adapter.setOnCountClickListner = object : CartAdapter.CountClickListner {
            override fun onClick(item: Cart) {
                cartViewModel.update(item)
                calculateTotalPrice()
            }

        }



        calculateTotalPrice()
        binding.checkOutButton.setOnClickListener(this)
        binding.cuponeBtn.setOnClickListener(this)
    }
}


    fun subscribeToLiveData() {

        viewModel.cartList.observe(viewLifecycleOwner, Observer {

            adapter.changeData(it, true)


        })

    }


    override fun onClick(v: View) {
        when (v) {
            binding.checkOutButton -> {
                if (viewModel.cartList.value!!.size != 0) {
                    orderRequest.totalPrice = totalPrice.toDouble();
                    val action =
                        CartFragmentDirections.actionCartFragmentToShipToFragment(orderRequest)
                    view?.findNavController()?.navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Select Item", Toast.LENGTH_SHORT).show()
                }
            }

            binding.cuponeBtn -> {
                cupone = binding.cuponeEt.text.toString()
                if (currentCupone.cuponeCode == cupone) {
                    binding.cuponeValue.text = currentCupone.cuponeCodeValue.toString() + "%"
                    calculateTotalPrice()
                } else {
                    binding.cuponeEt.setError("Cupone not found")
                }
            }
        }
    }




    fun calculateTotalPrice() {
        var itemCount = 0
        var price = 0.0f
        var cuponepercent = 0.000f


        viewModel.cartList.observe(requireActivity()) {
            for (item in it ) {
                itemCount += item.count
                cuponepercent =
                    (item.price * (currentCupone.cuponeCodeValue!!.toFloat() / 100.0f)).toFloat()
                price = (price + (item.count * item.price)).toFloat()
                totalPrice = (price + 13) - cuponepercent
            }

            if (it.size == 0) {
                binding.totalItemsCount.text = "$itemsStr (0)"
                binding.totalItemsPrice.text = "0.0"
                binding.totalPrice.text = "0.0"
            } else {
                binding.totalItemsCount.text = "$itemsStr ($itemCount)"
                binding.totalItemsPrice.text = price.toString() + " Egp"
                binding.totalPrice.text = String.format("%.2f", totalPrice) + " Egp"
            }
        }
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}