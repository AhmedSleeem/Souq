package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.FilterParams
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.ExploreFragmentDirections
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.SearchSucceedFragment
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.category.CategoryRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.recommended.RecommendedAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.recommended.RecommendedStateAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.saleProducts.SaleRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.saleProducts.SaleStateAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.saleProducts.SaleViewPagerAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.CartRoom
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.viewpager2.widget.ViewPager2
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), View.OnClickListener {

    var navController: NavController? = null
    private var lastSearches = mutableListOf("bag", "dress")
    private var saleData = arrayListOf<ProductResponse.Item>()

    private lateinit var viewPagerAdapter: SaleViewPagerAdapter
    private lateinit var recommendedAdapter: RecommendedAdapter
    private lateinit var categoryRecyclerAdapter: CategoryRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private val sliderHandler = Handler();
    private lateinit var viewModel: HomeViewModel
    private lateinit var saleRecyclerAdapter: SaleRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        viewPagerAdapter =
            SaleViewPagerAdapter(
                requireContext()
            )
        binding.saleViewPager.adapter = viewPagerAdapter
        binding.dotsIndicator.setViewPager2(binding.saleViewPager)


        categoryRecyclerAdapter = CategoryRecyclerAdapter(requireContext())
        binding.categoryRv.adapter = categoryRecyclerAdapter

        return view
    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()
    }


    private var getSaleJob: Job? = null
    private fun getSaleProducts() {
        try {
            // Make sure we cancel the previous job before creating a new one
            getSaleJob?.cancel()
            getSaleJob = lifecycleScope.launch {
                viewModel.getProductsHaveSale().collect {
                    binding.saleRv.visibility = View.VISIBLE
                    binding.retryView.visibility = View.INVISIBLE
                    binding.nestedScrollViewLayout.visibility = View.VISIBLE
                    binding.saleShimmer.hideShimmerAdapter()
                    saleRecyclerAdapter.submitData(it)
                }
            }
        } catch (e: Exception) {
            binding.retryView.visibility = View.VISIBLE
            binding.nestedScrollViewLayout.visibility = View.INVISIBLE
        }
    }

    private var getRecommendedJob: Job? = null
    private fun getRecommendedProducts() {
        try {
            // Make sure we cancel the previous job before creating a new one
            getRecommendedJob?.cancel()
            getRecommendedJob = lifecycleScope.launch {
                viewModel.getRecommended(FilterParams(title = CartRoom.lastSearch)).collect {
                    binding.recommendedRv.visibility = View.VISIBLE
                    binding.retryView.visibility = View.INVISIBLE
                    binding.nestedScrollViewLayout.visibility = View.VISIBLE
                    binding.recommendedProgress.hideShimmerAdapter()
                    recommendedAdapter.submitData(it)
                }
            }
        } catch (e: Exception) {
            binding.retryView.visibility = View.VISIBLE
            binding.nestedScrollViewLayout.visibility = View.INVISIBLE
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdded) {
            binding.saleShimmer.showShimmerAdapter()
            //init view model
            viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

            initSaleRecyclerView()
            initRecommendedRecyclerView()
            getSaleProducts()
            getRecommendedProducts()


            viewModel.getSaleItems()
            viewModel.getAllCategories()


            // search bar
            // enable binding.searchBar callbacks
            initSearchBar(view)

            binding.retryButton.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentSelf()
                it.findNavController().navigate(action)
            }
            //listeners
//            binding.notificationIv.setOnClickListener {
//                Navigation.findNavController(it)
//                    .navigate(R.id.action_homeFragment_to_notificationFragment);
//            }

            binding.favoriteIv.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_favoriteFragment);
            }

            binding.moreCategoryTv.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_listCategoryFragment);
            }

            binding.saleSeeMoreTv.setOnClickListener {
                onClick(it)
            }


            //recommended adapter item listener
            recommendedAdapter.itemClickListener = object : RecommendedAdapter.ItemClickListener {
                override fun onClick(view: View, item: ProductResponse.Item) {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item,null)
                    view.findNavController().navigate(action)
                }

            }

            binding.saleViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 3000)
                }
            })



            navController = Navigation.findNavController(view)
            view.findViewById<TextView>(R.id.moreCategory_tv).setOnClickListener(this)
            binding.moreCategoryTv.setOnClickListener(this)

        }
    }

    private fun initRecommendedRecyclerView() {
        recommendedAdapter =
            RecommendedAdapter()
        binding.recommendedRv.adapter = recommendedAdapter

        binding.recommendedRv.visibility = View.INVISIBLE
        binding.recommendedProgress.showShimmerAdapter()

        binding.recommendedRv.adapter = recommendedAdapter.withLoadStateHeaderAndFooter(
            header = RecommendedStateAdapter { recommendedAdapter.retry() },
            footer = RecommendedStateAdapter { recommendedAdapter.retry() }
        )

        recommendedAdapter.addLoadStateListener { loadState ->
            //val isEmptyList = loadState.refresh is LoadState.NotLoading && saleRecyclerAdapter.itemCount == 0
            //showEmptyList(isEmptyList)

            // Only show the list if refresh succeeds
            binding.recommendedRv.isVisible = loadState.source.refresh is LoadState.NotLoading

            // Show loading spinner during initial load or refresh
            binding.recommendErrorProgress.isVisible = loadState.source.refresh is LoadState.Loading

            // Show the retry state if initial load or refresh fails
            binding.recommendErrorIv.isVisible = loadState.source.refresh is LoadState.Error
            binding.recommendErrorRetry.isVisible = loadState.source.refresh is LoadState.Error
        }

        binding.recommendErrorRetry.setOnClickListener { recommendedAdapter.retry() }
    }

    private fun initSearchBar(view: View) {
        binding.searchBar.setOnSearchActionListener(object :
            MaterialSearchBar.OnSearchActionListener {
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
                CartRoom.lastSearch = text.toString()
                val action = HomeFragmentDirections.actionHomeFragmentToSearchSucceedFragment(
                    query = text.toString(),
                    searchStatus = SearchSucceedFragment.SearchStatus.QUERY
                )
                view.findNavController().navigate(action)
            }
        });
        binding.searchBar.lastSuggestions = lastSearches;
    }

    private fun initSaleRecyclerView() {
        saleRecyclerAdapter =
            SaleRecyclerAdapter()
        binding.saleRv.adapter = saleRecyclerAdapter

        binding.saleRv.visibility = View.INVISIBLE
        binding.saleShimmer.showShimmerAdapter()

        binding.saleRv.adapter = saleRecyclerAdapter.withLoadStateHeaderAndFooter(
            header = SaleStateAdapter { saleRecyclerAdapter.retry() },
            footer = SaleStateAdapter { saleRecyclerAdapter.retry() }
        )

        saleRecyclerAdapter.addLoadStateListener { loadState ->
            val isEmptyList =
                loadState.refresh is LoadState.NotLoading && saleRecyclerAdapter.itemCount == 0
            //showEmptyList(isEmptyList)

            // Only show the list if refresh succeeds
            binding.saleRv.isVisible = loadState.source.refresh is LoadState.NotLoading

            // Show loading spinner during initial load or refresh
             binding.saleeErrorProgress.isVisible = loadState.source.refresh is LoadState.Loading

            // Show the retry state if initial load or refresh fails
            binding.saleeErrorIv.isVisible = loadState.source.refresh is LoadState.Error
            binding.saleeErrorRetry.isVisible = loadState.source.refresh is LoadState.Error



        }

        binding.saleeErrorRetry.setOnClickListener { saleRecyclerAdapter.retry() }
    }

    private fun subscribeToLiveData() {
        // get all data to Recommended RecyclerView


        // get offer data to Sale RecyclerView and ViewPager
        viewModel.saleItemsLiveData.observe(requireActivity(), Observer {

            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.retryView.visibility = View.INVISIBLE
                    binding.nestedScrollViewLayout.visibility = View.VISIBLE
                    binding.viewPagerProgress.showShimmerAdapter()
                    binding.saleViewPager.visibility = View.GONE
                }

                Resource.Status.ERROR -> {
                    binding.retryView.visibility = View.VISIBLE
                    binding.nestedScrollViewLayout.visibility = View.INVISIBLE
                    binding.viewPagerProgress.hideShimmerAdapter()
                    binding.saleViewPager.visibility = View.VISIBLE

                }

                Resource.Status.SUCCESS -> {
                    it.data.let {
                        binding.viewPagerProgress.hideShimmerAdapter()
                        binding.nestedScrollViewLayout.visibility = View.VISIBLE
                        binding.saleViewPager.visibility = View.VISIBLE
                        binding.retryView.visibility = View.INVISIBLE
                        viewPagerAdapter.changeData(it!!)
                        binding.dotsIndicator.setViewPager2(binding.saleViewPager)
                    }
                }
            }
        })

        // get caegory to category RecyclerView and ViewPager
        viewModel.categoryLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.nestedScrollViewLayout.visibility = View.VISIBLE
                    binding.retryView.visibility = View.INVISIBLE
                    Log.e("sssss", "Loading........")
                    binding.categoryProgress.showShimmerAdapter()
                    binding.categoryRv.visibility = View.GONE
                }

                Resource.Status.ERROR -> {

                    binding.nestedScrollViewLayout.visibility = View.INVISIBLE
                    binding.retryView.visibility = View.VISIBLE
                    binding.categoryProgress.hideShimmerAdapter()
                    binding.categoryRv.visibility = View.GONE
                }

                Resource.Status.SUCCESS -> {
                    it.data.let {
                        binding.nestedScrollViewLayout.visibility = View.VISIBLE
                        binding.retryView.visibility = View.INVISIBLE
                        binding.categoryProgress.hideShimmerAdapter()
                        binding.categoryRv.visibility = View.VISIBLE
                        categoryRecyclerAdapter.changeData(it!!)
                    }
                }
            }
        })
    }

    private val sliderRunnable: Runnable = Runnable {
        kotlin.run {
            binding.saleViewPager.currentItem = binding.saleViewPager.currentItem + 1

            if (binding.saleViewPager.currentItem == saleData.size - 1) {
                saleData.shuffle()
                viewPagerAdapter.changeData(saleData)
                binding.saleViewPager.currentItem = 0
            }
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.moreCategoryTv -> {
                val action = HomeFragmentDirections.actionHomeFragmentToListCategoryFragment()
                view?.findNavController()?.navigate(action)
            }

            binding.saleSeeMoreTv -> {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToOfferTypeFragment("flash sale")
                view?.findNavController()?.navigate(action)
            }
        }
    }
}