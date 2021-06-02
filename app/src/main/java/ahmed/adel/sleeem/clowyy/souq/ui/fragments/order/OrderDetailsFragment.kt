package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOrderDetailsBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.OrdersByIdResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter.OrderProductsRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs

class OrderDetailsFragment : Fragment() {

    lateinit var binding: FragmentOrderDetailsBinding
    lateinit var adapter: OrderProductsRecyclerAdapter
    val args: OrderDetailsFragmentArgs by navArgs()
    lateinit var order: OrdersByIdResponse.OrderResponseItem
    lateinit var viewModel: OrderDetailsViewModel
    lateinit var itemsList: MutableList<ItemResponse>
    val shippingPrice = 40


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        order = args.order
        initViewModel()
        itemsList = mutableListOf()
        initProductsRecyclerView()
        orderStatus(order.orderState)
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        binding.dateShippingTv.text = order.orderDate
        binding.resiNoTv.text = order._id
        binding.addressTv.text = order.Address
        var itemsCount = 0.0
        var itemsPrice = 0.0
        for (itm in order.itemIds) {
            itemsCount += itm.count
            itemsPrice += itm.count
        }
        binding.itemsTv.text = "Items ($itemsCount)"
        binding.shippingPriceTv.text = "\$$shippingPrice"
        binding.importChargesTv.text = "\$${order.importCharge}"
        binding.itemsPriceTv.text = "\$${order.totalPrice}"
        val total = order.totalPrice + order.importCharge + shippingPrice
        binding.totalPrice.text = "\$${total}"
    }

    private fun initProductsRecyclerView() {
        adapter = OrderProductsRecyclerAdapter({ view, item, i ->
        }, requireContext())
        getItemById()
        adapter.changeData(itemsList)
        binding.productsRv.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(OrderDetailsViewModel::class.java)
        for (odr in order.itemIds)
            viewModel.getItemsById(odr.id)
    }

    private fun orderStatus(orderState: String) {
        when (orderState.toLowerCase()) {

            Status.PACKING.tag -> {
                changePackingUI()
            }
            Status.SHIPPING.tag -> {
                changeShippingUI()
            }
            Status.ARRIVING.tag -> {
                changeArrivingUI()
            }
            Status.SUCCESS.tag -> {
                changeSuccessUI()
            }
        }
    }

    private fun changeSuccessUI() {
        changeArrivingUI()
        changeOrderStatusUI(binding.successLine, R.color.primaryBlue)
        changeOrderStatusUI(binding.successCheck, R.drawable.order_details_shipping_check_blue)
    }

    private fun changeArrivingUI() {
        changeShippingUI()
        changeOrderStatusUI(binding.arrivingLine, R.drawable.order_details_shipping_check_blue)
        changeOrderStatusUI(binding.arrivingCheck, R.drawable.order_details_shipping_check_blue)
    }

    private fun changeShippingUI() {
        changePackingUI()
        changeOrderStatusUI(binding.shippingLine, R.color.primaryBlue)
        changeOrderStatusUI(binding.shippingCheck, R.drawable.order_details_shipping_check_blue)
    }

    private fun changePackingUI() {
        changeOrderStatusUI(
            binding.packingCheck,
            R.drawable.order_details_shipping_check_blue
        )
    }

    fun <T : View> changeOrderStatusUI(view: T, res: Int) {
        view.setBackgroundResource(res)
    }

    enum class Status(val tag: String) {
        PACKING("packing"),
        SHIPPING("shipping"),
        ARRIVING("arriving"),
        SUCCESS("success")
    }

    private fun getItemById() {
        viewModel.item.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("TAG", "getAllOrders: LOADING")
                    binding.shimmerProductsRv.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    Log.e("TAG", "getAllOrders: ERROR" + it.message)
                    binding.shimmerProductsRv.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    binding.shimmerProductsRv.visibility = View.GONE
                    it.data.let {
                        Log.e("TAG", "getAllOrders: ERROR" + it?.size)
                        itemsList.add(it!!)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}