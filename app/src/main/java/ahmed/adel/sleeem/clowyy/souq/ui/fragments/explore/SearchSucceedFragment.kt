package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSearchSucceedBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.FilterParams
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.adapter.SearchSucceedAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog.*
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    lateinit var filterParams:FilterParams;

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
        filterParams=FilterParams()
        binding.searchRv.adapter = searchRecyclerAdapter
        searchByStatus()



        //init searchView
        binding.searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                val s = if (enabled) binding.filterBar.visibility=View.GONE else binding.filterBar.visibility=View.VISIBLE

            }

            override fun onSearchConfirmed(text: CharSequence?) {
                if (text.toString().isNotBlank() && text.toString().isNotEmpty()){
                    binding.searchBar.setPlaceHolder(text)
                    viewModel.getItemsByQuery(text.toString())
                    filterParams = FilterParams()
                    changeFilterTextViewState(
                        categoryTv = true,
                        priceTv = true,
                        brandTv = true,
                        saleTv = true,
                        selected = false
                    )
                }
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
                if (min>0 || max<10000) {
                    filterParams.min = min
                    filterParams.max = max
                    filterParams.price = 1
                    changeFilterTextViewState(priceTv = true,selected =true)
                }else
                    changeFilterTextViewState(priceTv = true,selected =false)
            }
        }

        categoryDialogFragment.mListener = object :FilterByCategoryBottomDialogFragment.ItemClickListener{
            override fun onItemClick(category: String?) {

                if (category != null){
                    filterParams.category = category
                    changeFilterTextViewState(categoryTv = true , selected = true)
                }else {
                    changeFilterTextViewState(categoryTv = true , selected = false)
                }

            }
        }

        brandsDialogFragment.mListener = object :FilterByBrandBottomDialogFragment.ItemClickListener{
            override fun onItemClick(brand: String?) {

                if (brand != null){
                    filterParams.brand = brand
                    changeFilterTextViewState(brandTv = true ,selected = true)
                }else {
                    changeFilterTextViewState(brandTv = true , selected = false)
                }
            }

        }

        saleDialogFragment.mListener = object :FilterBySaleBottomDialogFragment.ItemClickListener{
            override fun onItemClick(sale: Int) {
                filterParams.sale = sale
                if (sale == 1) changeFilterTextViewState(saleTv = true , selected = true)
                else  changeFilterTextViewState(saleTv = true , selected = false)
            }
        }


    }


    private fun searchByStatus() {
        when(args.searchStatus){
            SearchStatus.CATEGORY ->{
                viewModel.getItemsByCategory(args.query)
                filterParams.category = args.query
            }
            SearchStatus.QUERY -> {
                viewModel.getItemsByQuery(args.query)
                filterParams.title=args.query
                binding.searchBar.setPlaceHolder(args.query)
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.LOADING->{
                    binding.searchRvShimmer.showShimmerAdapter()
                    binding.searchRv.visibility = View.GONE

                }
                Resource.Status.SUCCESS->{
                    if(it.flag==1)
                        searchRecyclerAdapter.changeData(it.data!!,true)
                    else
                        searchRecyclerAdapter.changeData(it.data!!)

                    viewModel.getCategoriesAndCount()
                    binding.resultCount.text = it.data.size.toString()
                    binding.searchRv.visibility = View.VISIBLE
                    binding.searchFailedView.visibility = View.GONE
                    binding.searchRvShimmer.hideShimmerAdapter()
                    binding.searchRv.visibility = View.VISIBLE
                }
                Resource.Status.ERROR->{

                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    Log.e("erorrrrrrrrrrrrrrrrrr", it.message.toString() )
                    binding.resultCount.text = "0"
                    binding.searchRv.visibility = View.GONE
                    binding.searchFailedView.visibility = View.VISIBLE
                    binding.searchRvShimmer.hideShimmerAdapter()

                }
            }
        })


    }

    fun changeFilterTextViewState(categoryTv:Boolean=false, priceTv:Boolean=false, brandTv:Boolean=false, saleTv:Boolean=false, selected:Boolean){
        if (categoryTv){
            if (!selected) {
                binding.filterCategoryTV.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_shape)
                FilterByCategoryBottomDialogFragment.position=-1
                filterParams.category = null
                changeFilterTextViewState(brandTv = true, selected = false)


            }else {
                binding.filterCategoryTV.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_selected_shape)
              }
            }

        if (brandTv){
            if (!selected){
                binding.filterBrandTV.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_shape)
                FilterByBrandBottomDialogFragment.position = -1
                filterParams.brand=null
            }else{
                binding.filterBrandTV.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_selected_shape)
            }
        }

        if (priceTv){
            if (!selected){
                binding.filterPriceTV.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_shape)
                filterParams.price = 0
                filterParams.min = null
                filterParams.max = null
            }else{
                binding.filterPriceTV.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_selected_shape)
            }
        }

        if (saleTv){
            if (!selected){
                binding.filterSaleTV.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_shape)
                FilterBySaleBottomDialogFragment.position = -1
            }else{
                binding.filterSaleTV.background = requireActivity().resources.getDrawable(R.drawable.filter_btn_selected_shape)
            }

        }

        viewModel.filterProducts(filterParams)
    }

    private fun<T: BottomSheetDialogFragment> showBottomShortByDialog(type:T , tag:String) {
        type.show(
            requireActivity().supportFragmentManager,
            tag
        )
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




}

