package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentExploreBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter.ExploreCategoryAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ExploreFragment : Fragment() {

    private var manRecyclerView: RecyclerView? = null
    private var woManRecyclerView: RecyclerView? = null
    private var manExploreCategoryAdapter: ExploreCategoryAdapter? = null
    private var womanExploreCategoryAdapter: ExploreCategoryAdapter? = null
    private lateinit var binding: FragmentExploreBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manRecyclerView = view.findViewById(R.id.category_man_fashion)
        woManRecyclerView = view.findViewById(R.id.category_woman_fashion)


        manExploreCategoryAdapter = ExploreCategoryAdapter(items = manList)
        womanExploreCategoryAdapter = ExploreCategoryAdapter(items = womanList)
        binding.categoryManFashion.adapter = manExploreCategoryAdapter
        binding.categoryWomanFashion.adapter = womanExploreCategoryAdapter

        manRecyclerView?.adapter = manExploreCategoryAdapter
        woManRecyclerView?.adapter = womanExploreCategoryAdapter

    }

    var manList = mutableListOf<ExplorerItem>(
        ExplorerItem(R.drawable.ic_shirt, "Man Shirt"),
        ExplorerItem(R.drawable.ic_dress, "Man Work Equipment"),
        ExplorerItem(R.drawable.ic_dress, "Man T-Shirt"),
        ExplorerItem(R.drawable.ic_dress, "Man Shoes"),
        ExplorerItem(R.drawable.ic_dress, "Man Pants"),
        ExplorerItem(R.drawable.ic_dress, "Man Underwear")
    )

    var womanList = mutableListOf<ExplorerItem>(
        ExplorerItem(R.drawable.ic_dress, "Dress"),
        ExplorerItem(R.drawable.ic_dress, "Woman T-Shirt"),
        ExplorerItem(R.drawable.ic_dress, "Woman Pants"),
        ExplorerItem(R.drawable.ic_dress, "Skirt"),
        ExplorerItem(R.drawable.ic_dress, "Woman Bag"),
        ExplorerItem(R.drawable.ic_dress, "High Heels"),
        ExplorerItem(R.drawable.ic_dress, "Bikini")

    )

}