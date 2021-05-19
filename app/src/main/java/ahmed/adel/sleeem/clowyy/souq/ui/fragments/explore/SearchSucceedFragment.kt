package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSearchSucceedBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter.SearchSucceedAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

class SearchSucceedFragment : Fragment() {

    private lateinit var searchRecyclerAdapter: SearchSucceedAdapter
    private lateinit var binding: FragmentSearchSucceedBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchSucceedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCategory.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchSucceedFragment_to_listCategoryFragment);
        }

        binding.filterIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchSucceedFragment_to_filterFragment);
        }

        binding.shortIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchSucceedFragment_to_shortByFragment);
        }

        searchRecyclerAdapter = SearchSucceedAdapter(items = list1)
        binding.searchRv.adapter = searchRecyclerAdapter


    }

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

}