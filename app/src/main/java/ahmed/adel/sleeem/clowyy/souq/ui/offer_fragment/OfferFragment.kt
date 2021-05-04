package ahmed.adel.sleeem.clowyy.souq.ui.offer_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOfferBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import ahmed.adel.sleeem.clowyy.souq.ui.home_fragment.adapter.CategoryRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.home_fragment.adapter.RecommendedRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.home_fragment.adapter.SaleRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.home_fragment.adapter.SaleViewPagerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.offer_fragment.adapter.OfferAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class OfferFragment : Fragment() {


    private lateinit var saleRecyclerAdapter: OfferAdapter
    private lateinit var binding: FragmentOfferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfferBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var list1 = mutableListOf<SaleItem>(
            SaleItem(
                R.drawable.bag2,
                "FS - Nike Air Max 270 React...",
                "24% Off",
                299.34f,
                534.34f
            ),
            SaleItem(
                R.drawable.shoes,
                "FS - Nike Air Max 270 React...",
                "24% Off",
                299.34f,
                534.34f
            ),
            SaleItem(
                R.drawable.shoes2,
                "FS - Nike Air Max 270 React...",
                "24% Off",
                299.34f,
                534.34f
            ),
            SaleItem(
                R.drawable.womem_bag,
                "FS - Nike Air Max 270 React...",
                "24% Off",
                299.34f,
                534.34f
            ),
            SaleItem(
                R.drawable.shoes,
                "FS - Nike Air Max 270 React...",
                "24% Off",
                299.34f,
                534.34f
            ),
            SaleItem(
                R.drawable.bag2,
                "FS - Nike Air Max 270 React...",
                "24% Off",
                299.34f,
                534.34f
            ),
            SaleItem(
                R.drawable.shoes2,
                "FS - Nike Air Max 270 React...",
                "24% Off",
                299.34f,
                534.34f
            ),
        )
        saleRecyclerAdapter = OfferAdapter(items = list1)
        binding.recommendedRv.adapter = saleRecyclerAdapter


    }

}