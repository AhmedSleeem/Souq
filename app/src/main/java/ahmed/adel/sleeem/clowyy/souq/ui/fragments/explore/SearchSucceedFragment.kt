package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSearchSucceedBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter.SearchSucceedAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.ExploreViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.SearchSucceedFragmentArgs
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
class SearchSucceedFragment : Fragment() {

    private lateinit var searchRecyclerAdapter: SearchSucceedAdapter
    private lateinit var binding: FragmentSearchSucceedBinding
    private lateinit var viewModel : ExploreViewModel
    private lateinit var categoryName : String
    private lateinit var searchView :SearchView
    private lateinit var list : ArrayList<ProductResponse.Item>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        searchView = binding.searchView

        binding = FragmentSearchSucceedBinding.inflate(inflater, container, false)
        val view = binding.root

//        arguments?.let {
//            val args = SearchSucceedFragmentArgs.fromBundle(it)
//            categoryName = args.categoryName
//        }

        searchRecyclerAdapter = SearchSucceedAdapter(requireContext())
        binding.searchRv.adapter = searchRecyclerAdapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ExploreViewModel::class.java);
        viewModel.getItemsByCategory(categoryName)
        viewModel.getItems()


        binding.tvCategory.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchSucceedFragment_to_listCategoryFragment);
        }

        binding.filterIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchSucceedFragment_to_filterFragment);
        }

        binding.shortIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchSucceedFragment_to_shortByFragment);
        }





    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
        // get data by category
        if(categoryName != "") {
            viewModel.filterLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.e("sssss", "Loading........")
                        binding.itemProgress.visibility = View.VISIBLE
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        binding.itemProgress.visibility = View.GONE

                    }
                    Resource.Status.SUCCESS -> {
                        it.data.let {
                            searchRecyclerAdapter.changeData(it!!)
                            binding.itemProgress.visibility = View.GONE
                        }
                    }
                }
            })
        }
        else {
            // get all data
            viewModel.itemsLiveData.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.e("sssss", "Loading........")
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.SUCCESS -> {
                        it.data.let {
                            searchRecyclerAdapter.changeData(it!!)
                        }
                    }
                }
            })
        }
    }



}