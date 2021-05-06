package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOrderBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.Order
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

class OrderFragment : Fragment() {

    lateinit var binding:FragmentOrderBinding
    lateinit var adapter:OrderRecyclerAdapter
    val data = listOf<Order>(
        Order("LQNSU346JK","Order at E-comm : August 1, 2017","Shipping",2,299.50f),
        Order("SDG1345KJD","Order at E-comm : August 1, 2017","Shipping",1,350.50f),
        Order("SDG1345KJD","Order at E-comm : August 5, 2017","Shipping",10,3500f)
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        adapter= OrderRecyclerAdapter(data)
        binding.ordersRv.adapter = adapter


    }
}