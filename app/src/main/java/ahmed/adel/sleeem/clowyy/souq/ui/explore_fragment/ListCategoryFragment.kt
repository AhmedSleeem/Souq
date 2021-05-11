package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentListCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSearchSucceedBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter.ListCategoryAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter.SearchSucceedAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class ListCategoryFragment : Fragment() {

    private lateinit var categoryListRecyclerAdapter: ListCategoryAdapter
    private lateinit var binding: FragmentListCategoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        categoryListRecyclerAdapter = ListCategoryAdapter(items = list)
        binding.categoryListRv.adapter = categoryListRecyclerAdapter

    }

    var list = mutableListOf<ExplorerItem>(
        ExplorerItem(R.drawable.ic_shirt, "Shirt"),
        ExplorerItem(R.drawable.ic_bikini, "Bikini"),
        ExplorerItem(R.drawable.ic_dress, "Dress"),
        ExplorerItem(R.drawable.ic_man_bag, "Work Equipment"),
        ExplorerItem(R.drawable.ic_man_pants, "Man Pants"),
        ExplorerItem(R.drawable.ic_man_shoes, "Man Shoes"),
        ExplorerItem(R.drawable.ic_man_underwear, "Man Underwear"),
        ExplorerItem(R.drawable.ic_woman_tshirt, "Man T-Shirt"),
        ExplorerItem(R.drawable.ic_woman_bag, "Woman Bag"),
        ExplorerItem(R.drawable.ic_woman_pants, "Woman Pants"),
        ExplorerItem(R.drawable.ic_woman_shoes, "High Heels"),
        ExplorerItem(R.drawable.ic_woman_tshirt, "Woman T-Shirt"),
        ExplorerItem(R.drawable.ic_skirt, "Skirt")
        )


}