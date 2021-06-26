package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOrderDetailsBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.DeleteOrderRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.OrderResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.OrdersByIdResponse
import ahmed.adel.sleeem.clowyy.souq.ui.activity.login.LoginActivity
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter.OrderProductsRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.app.AlertDialog
import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar

class OrderDetailsFragment : Fragment(), View.OnClickListener {

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
        binding.itemsTv.text = requireContext().resources.getString(R.string.items)+" ($itemsCount)"
        binding.shippingPriceTv.text = "$shippingPrice EGP"
        binding.importChargesTv.text = "${order.importCharge} EGP"
        binding.itemsPriceTv.text = String.format("%.2f", order.totalPrice) + " Egp"
        val total = order.totalPrice + order.importCharge + shippingPrice
        binding.totalPrice.text = String.format("%.2f", total) + " Egp"
        binding.retryButton.setOnClickListener {
            val action = OrderDetailsFragmentDirections.actionOrderDetailsFragmentSelf(order)
            it.findNavController().navigate(action)
        }
        binding.button.setOnClickListener(this)
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
        for (odr in order.itemIds) {
            Log.e("TAG", "initViewModel: id==>>" + odr.id)
            viewModel.getItemsById(odr.id)
        }
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
        changeOrderStatusUI(binding.successCheck, R.drawable.order_details_shipping_check_blue)
    }

    private fun changeArrivingUI() {
        changeShippingUI()
        changeOrderStatusUI(binding.arrivingCheck, R.drawable.order_details_shipping_check_blue)
        changeOrderStatusUI(binding.successLine, R.color.primaryBlue)
    }

    private fun changeShippingUI() {
        changePackingUI()
        changeOrderStatusUI(binding.shippingCheck, R.drawable.order_details_shipping_check_blue)
        changeOrderStatusUI(binding.arrivingLine, R.color.primaryBlue)
    }

    private fun changePackingUI() {
        changeOrderStatusUI(binding.packingCheck, R.drawable.order_details_shipping_check_blue)
        changeOrderStatusUI(binding.shippingLine, R.color.primaryBlue)
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
                    binding.retryView.visibility = View.INVISIBLE
                    binding.shimmerProductsRv.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    Log.e("TAG", "getAllOrders: ERROR" + it.message)
                    binding.retryView.visibility = View.VISIBLE
                    binding.shimmerProductsRv.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    binding.retryView.visibility = View.INVISIBLE
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

    override fun onClick(v: View?) {
        when (v) {
            binding.button -> {
                if (checkStatus()) {

                    showConfirmDeleteDialog()
                } else {
                    Snackbar.make(
                        binding.root,
                        "You Can`t delete this order now",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showConfirmDeleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("CANCEL ORDER")
            .setMessage("Be Careful You Will Delete your Order .")
            .setPositiveButton("Yes") { dialogInterface, which ->
                viewModel.deleteOrderById(DeleteOrderRequest(order._id))
                deleteOrder()
            }
            .setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(requireContext(), "Deleted Canceled.", Toast.LENGTH_LONG).show()
            }.show()
    }

    private fun deleteOrder() {
        viewModel.deleteOrder.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                Resource.Status.SUCCESS -> {

                    it.data.let {
                        Toast.makeText(
                            requireContext(),
                            it!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigateUp()
                    }
                }
            }
        })
    }

    private fun checkStatus(): Boolean {
        return order.orderState.toLowerCase() == Status.PACKING.tag || order.orderState.toLowerCase() == Status.SHIPPING.tag
    }
}