package ahmed.adel.sleeem.clowyy.souq.ui.fragments.offer_type_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOfferTypeBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.offer_type_fragment.adapter.OfferTypeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class OfferTypeFragment : Fragment() {


    private lateinit var saleRecyclerAdapter: OfferTypeAdapter
    private lateinit var binding: FragmentOfferTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfferTypeBinding.inflate(inflater, container, false)
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

        saleRecyclerAdapter = OfferTypeAdapter(items = list1)
        binding.recommended.adapter = saleRecyclerAdapter

    }

}