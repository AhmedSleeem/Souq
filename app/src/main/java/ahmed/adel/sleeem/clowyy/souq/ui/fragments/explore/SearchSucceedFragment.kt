package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSearchSucceedBinding
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.adapter.SearchSucceedAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mancj.materialsearchbar.MaterialSearchBar

class SearchSucceedFragment : Fragment() , View.OnClickListener  {

    private lateinit var searchRecyclerAdapter: SearchSucceedAdapter
    private lateinit var binding: FragmentSearchSucceedBinding
    private val shortByDialogFragment = ShortByBottomDialogFragment.newInstance()
    private val priceDialogFragment = FilterByPriceBottomDialogFragment.newInstance()
    private val categoryDialogFragment = FilterByCategoryBottomDialogFragment.newInstance()
    private val brandsDialogFragment = FilterByBrandBottomDialogFragment.newInstance()
    private val saleDialogFragment = FilterBySaleBottomDialogFragment.newInstance()
    private val args by navArgs<SearchSucceedFragmentArgs>()
    private lateinit var viewModel: SearchResultViewModel

    private var lastSearches = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchSucceedBinding.inflate(inflater, container, false)

        searchRecyclerAdapter = SearchSucceedAdapter(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init view model
        viewModel = ViewModelProvider(requireActivity()).get(SearchResultViewModel::class.java)
        subscribeToLiveData()

        searchByStatus()



        //init searchView
        binding.searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                val s = if (enabled) binding.filterBar.visibility=View.GONE else binding.filterBar.visibility=View.VISIBLE
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                if (text.toString().isNotBlank() && text.toString().isNotEmpty())
                    binding.searchBar.setPlaceHolder(text)
               viewModel.getItemsByQuery(text.toString())
            }

        });

        binding.searchBar.lastSuggestions = lastSearches;


        //listeners
        binding.shortIv.setOnClickListener(this)
        binding.filterPriceTV.setOnClickListener(this)
        binding.filterCategoryTV.setOnClickListener(this)
        binding.filterBrandTV.setOnClickListener(this)
        binding.filterSaleTV.setOnClickListener(this)

        shortByDialogFragment.mListener = object : ShortByBottomDialogFragment.ItemClickListener{
            override fun onItemClick(item: Int) {
                viewModel.shortData(item)
            }
        }

        priceDialogFragment.mListener = object : FilterByPriceBottomDialogFragment.ItemClickListener{
            override fun onItemClick(min: Int, max: Int) {
                changeFilterTextViewBackground(binding.filterPriceTV,false)
               viewModel.filterByPrice(min,max)
            }

        }

        categoryDialogFragment.mListener = object :FilterByCategoryBottomDialogFragment.ItemClickListener{
            override fun onItemClick(category: String) {
                viewModel.filterByCategory(category)
                if (FilterByCategoryBottomDialogFragment.position == -1) {
                    changeFilterTextViewBackground(binding.filterCategoryTV,true)
                    searchByStatus()
                }else
                    changeFilterTextViewBackground(binding.filterCategoryTV,false)
            }

        }

        brandsDialogFragment.mListener = object :FilterByBrandBottomDialogFragment.ItemClickListener{
            override fun onItemClick(brand: String) {
                viewModel.filterByBrands(brand)
            }
        }

        saleDialogFragment.mListener = object :FilterBySaleBottomDialogFragment.ItemClickListener{
            override fun onItemClick(isBySale: Boolean) {
                viewModel.filteredBySale(isBySale)
            }
        }

        binding.searchRv.adapter = searchRecyclerAdapter
    }

    private fun searchByStatus() {
        when(args.searchStatus){
            SearchStatus.CATEGORY -> viewModel.getItemsByCategory(args.query)
            SearchStatus.QUERY -> viewModel.getItemsByQuery(args.query)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.LOADING->{}
                Resource.Status.SUCCESS->{
                    searchRecyclerAdapter.changeData(it.data!!)
                }
                Resource.Status.ERROR->{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.filteredLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.LOADING->{}
                Resource.Status.SUCCESS->{
                    searchRecyclerAdapter.changeData(it.data!!,true)
                }
            }
        })

    }

    fun changeFilterTextViewBackground(textView:TextView , isActive:Boolean){
        if (isActive)
            textView.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_shape)
        else
            textView.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_selected_shape)


    }
    private fun<T: BottomSheetDialogFragment> showBottomShortByDialog(type:T , tag:String) {
        type.show(
            requireActivity().supportFragmentManager,
            tag
        )
    }

    enum class SearchStatus {
        CATEGORY,
        QUERY
    }

    enum class ShortBy(val tag:Int){
        BestMatch(0) ,
        LowToHigh(1) ,
        HighToLow(2) ,
        TopRated(3)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.shortIv->{
                showBottomShortByDialog(shortByDialogFragment,ShortByBottomDialogFragment.TAG)
            }

            binding.filterPriceTV->{
                showBottomShortByDialog(priceDialogFragment,FilterByPriceBottomDialogFragment.TAG)
            }

            binding.filterCategoryTV->{
                showBottomShortByDialog(categoryDialogFragment,FilterByCategoryBottomDialogFragment.TAG)
            }

            binding.filterBrandTV->{
                showBottomShortByDialog(brandsDialogFragment,FilterByBrandBottomDialogFragment.TAG)
            }

            binding.filterSaleTV->{
                showBottomShortByDialog(saleDialogFragment,FilterBySaleBottomDialogFragment.TAG)
            }
        }
    }


}