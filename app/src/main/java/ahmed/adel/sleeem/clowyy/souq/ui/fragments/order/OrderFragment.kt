package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOrderBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.OrderItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter.OrderRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
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
import androidx.navigation.findNavController

class OrderFragment : Fragment() {

    lateinit var binding: FragmentOrderBinding
    lateinit var adapter: OrderRecyclerAdapter
    lateinit var viewModel: OrderViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        binding.retryButton.setOnClickListener {
            val action = OrderFragmentDirections.actionOrderFragmentSelf()
            it.findNavController().navigate(action)
        }
        initViewModel()
        initOrderRecyclerView()
        viewModel.getOrders(LoginUtils.getInstance(requireContext())!!.userInfo()._id!!)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

    }

    private fun initOrderRecyclerView() {
        adapter = OrderRecyclerAdapter { view, orderResponseItem, i ->
            val action = OrderFragmentDirections.actionOrderFragmentToOrderDetailsFragment(orderResponseItem)
            view.findNavController().navigate(action)

        }
        getAllOrders()
        binding.ordersRv.adapter = adapter
    }

    private fun getAllOrders() {
        viewModel.orders.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("TAG", "getAllOrders: LOADING" )
                    binding.progressBar.visibility = View.VISIBLE
                    binding.retryView.visibility = View.INVISIBLE
                }
                Resource.Status.ERROR -> {
                    Log.e("TAG", "getAllOrders: ERROR"+it.message )
                    binding.retryView.visibility = View.VISIBLE
                    if(it.message.toString() != "timeout")
                    binding.textView6.text = it.message.toString()
                    binding.progressBar.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    binding.retryView.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.GONE
                    it.data.let {
                        Log.e("TAG", "getAllOrders: ERROR"+it?.size )
                        adapter.changeData(it!!)
                        binding.ordersRv.adapter = adapter
                    }
                }
            }
        })
    }

}