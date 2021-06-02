package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOrderDetailsBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.OrderProductItem
import ahmed.adel.sleeem.clowyy.souq.pojo.OrderResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.itemResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter.OrderProductsRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs

class OrderDetailsFragment : Fragment() {

    lateinit var binding: FragmentOrderDetailsBinding
    lateinit var adapter: OrderProductsRecyclerAdapter
    val args: OrderDetailsFragmentArgs by navArgs()
    lateinit var order: OrderResponse.OrderResponseItem
    lateinit var viewModel: OrderDetailsViewModel
    lateinit var itemsList: MutableList<itemResponse>
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
        var itemsCount = 0
        var itemsPrice = 0
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
            "packing" -> {
                binding.packingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.shippingLine.setBackgroundResource(R.color.overlayGray)
                binding.shippingCheck.setImageResource(R.drawable.order_details_shipping_check_gray)
                binding.arrivingLine.setBackgroundResource(R.color.overlayGray)
                binding.arrivingCheck.setImageResource(R.drawable.order_details_shipping_check_gray)
                binding.successLine.setBackgroundResource(R.color.overlayGray)
                binding.successCheck.setImageResource(R.drawable.order_details_shipping_check_gray)
            }
            "shipping" -> {
                binding.packingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.shippingLine.setBackgroundResource(R.color.primaryBlue)
                binding.shippingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.arrivingLine.setBackgroundResource(R.color.overlayGray)
                binding.arrivingCheck.setImageResource(R.drawable.order_details_shipping_check_gray)
                binding.successLine.setBackgroundResource(R.color.overlayGray)
                binding.successCheck.setImageResource(R.drawable.order_details_shipping_check_gray)
            }
            "arriving" -> {
                binding.packingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.shippingLine.setBackgroundResource(R.color.primaryBlue)
                binding.shippingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.arrivingLine.setBackgroundResource(R.color.primaryBlue)
                binding.arrivingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.successLine.setBackgroundResource(R.color.overlayGray)
                binding.successCheck.setImageResource(R.drawable.order_details_shipping_check_gray)
            }
            "success" -> {
                binding.packingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.shippingLine.setBackgroundResource(R.color.primaryBlue)
                binding.shippingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.arrivingLine.setBackgroundResource(R.color.primaryBlue)
                binding.arrivingCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
                binding.successLine.setBackgroundResource(R.color.primaryBlue)
                binding.successCheck.setImageResource(R.drawable.order_details_shipping_check_blue)
            }
        }
    }

    private fun getItemById() {
        viewModel.item.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("TAG", "getAllOrders: LOADING")
                    binding.itemProgressBar.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    Log.e("TAG", "getAllOrders: ERROR" + it.message)
                    binding.itemProgressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    binding.itemProgressBar.visibility = View.GONE
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