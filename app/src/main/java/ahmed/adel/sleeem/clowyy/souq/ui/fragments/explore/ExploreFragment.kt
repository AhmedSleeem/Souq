package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentExploreBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.CategoryResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.adapter.ExploreCategoryAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.HomeFragmentDirections
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mancj.materialsearchbar.MaterialSearchBar


class ExploreFragment : Fragment() {

    lateinit var categoryAdapter: ExploreCategoryAdapter
    private lateinit var binding: FragmentExploreBinding
    lateinit var viewModel:ExploreViewModel;



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        categoryAdapter = ExploreCategoryAdapter(requireContext())

        return binding.root
    }

    private var lastSearches = mutableListOf<String>("mahmoud","hager")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init view model
        viewModel = ViewModelProvider(requireActivity()).get(ExploreViewModel::class.java)
        subscribeToLiveData()
        viewModel.getCategories()

        //enable binding.searchBar callbacks
        binding.searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {
//                when (buttonCode) {
//                    MaterialSearchBar.B -> //openVoiceRecognizer()
//                }
            }

            override fun onSearchStateChanged(enabled: Boolean) {
//                val s = if (enabled) "enabled" else "disabled"
//                Toast.makeText(requireContext(), "Search $s", Toast.LENGTH_SHORT).show()
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                val action = ExploreFragmentDirections.actionExploreFragmentToSearchSucceedFragment(query = text.toString() ,
                    searchStatus = SearchSucceedFragment.SearchStatus.QUERY)
                view.findNavController().navigate(action)
            }

        });
        binding.searchBar.lastSuggestions = lastSearches;



        //category click listener
        categoryAdapter.setOnItemClickListener = object : ExploreCategoryAdapter.OnItemClickListener{
            override fun onClick(
                view: View,
                item: CategoryResponse.CategoryResponseItem
            ) {
                val action = ExploreFragmentDirections.actionExploreFragmentToSearchSucceedFragment(query = item.name ,
                    searchStatus = SearchSucceedFragment.SearchStatus.CATEGORY)
                view.findNavController().navigate(action)
            }
        }

//        binding.notificationIv.setOnClickListener {
//            Navigation.findNavController(it)
//                .navigate(R.id.action_exploreFragment_to_searchSucceedFragment);
//        }
//        binding.favoriteIv.setOnClickListener {
//            Navigation.findNavController(it)
//                .navigate(R.id.action_exploreFragment_to_favoriteFragment);
//        }

        binding.categoryRv.adapter = categoryAdapter
    }

    private fun subscribeToLiveData() {
        viewModel.categoriesLiveData.observe(requireActivity(), Observer {
            when(it.status){
                Resource.Status.SUCCESS ->{
                    categoryAdapter.changeData(it.data!!)
                }
                Resource.Status.ERROR ->{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING->{

                }
            }
        })
    }

}