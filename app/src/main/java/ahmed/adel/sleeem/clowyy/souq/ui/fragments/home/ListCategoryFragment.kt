package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentListCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter.ListCategoryAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.HomeViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class ListCategoryFragment : Fragment() {

    private lateinit var categoryListRecyclerAdapter: ListCategoryAdapter
    private lateinit var binding: FragmentListCategoryBinding
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        categoryListRecyclerAdapter = ListCategoryAdapter(requireContext())
        binding.categoryListRv.adapter = categoryListRecyclerAdapter

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java);
        viewModel.getAllCategories()

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()

    }


    private fun subscribeToLiveData() {

        viewModel.categoryLiveData.observe(requireActivity(), Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Log.e("sssss","Loading........")
                   // binding.viewPagerProgress.visibility = View.VISIBLE
                }
                Resource.Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_LONG).show()
                   // binding.viewPagerProgress.visibility = View.GONE
                }
                Resource.Status.SUCCESS->{
                    it.data.let {
                      //  binding.viewPagerProgress.visibility = View.GONE
                        categoryListRecyclerAdapter.changeData(it!!)
                        Log.e("sss", it!!.get(1).url)
                        Log.e("sss",  "" + it.size)
                    }
                }
            }
        })
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