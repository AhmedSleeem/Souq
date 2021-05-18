package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOrderDetailsBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.OrderProductItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter.OrderProductsRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

class OrderDetailsFragment : Fragment() {

    lateinit var binding:FragmentOrderDetailsBinding
    lateinit var adapter:OrderProductsRecyclerAdapter
    val data = listOf<OrderProductItem>(
        OrderProductItem("Nike Air Zoom Pegasus 36 Miami",299.99f,R.drawable.shoes,true),
        OrderProductItem("Nike Air Zoom Pegasus 36 Miami",250.99f,R.drawable.shoes2,false)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        adapter = OrderProductsRecyclerAdapter(data)
        binding.productsRv.adapter = adapter


    }


}