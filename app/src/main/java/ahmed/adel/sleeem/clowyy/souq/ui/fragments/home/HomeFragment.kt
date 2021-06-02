package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home

import ahmed.adel.sleeem.clowyy.souq.*
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.SearchSucceedFragment
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.CategoryRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.RecommendedRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.SaleRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.SaleViewPagerAdapter

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mancj.materialsearchbar.MaterialSearchBar


class HomeFragment : Fragment() , View.OnClickListener {

    var navController : NavController? = null
    private var lastSearches = mutableListOf<String>("mahmoud","hager")
    private var saleData = arrayListOf<ProductResponse.Item>()

    private lateinit var viewPagerAdapter: SaleViewPagerAdapter
    private lateinit var saleRecyclerAdapter: SaleRecyclerAdapter
    private lateinit var recommendedRecyclerAdapter: RecommendedRecyclerAdapter
    private lateinit var categoryRecyclerAdapter: CategoryRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private val sliderHandler = Handler();
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        recommendedRecyclerAdapter = RecommendedRecyclerAdapter(requireContext());
        binding.recommendedRv.adapter = recommendedRecyclerAdapter

        viewPagerAdapter = SaleViewPagerAdapter(requireContext())
        binding.saleViewPager.adapter = viewPagerAdapter
        binding.dotsIndicator.setViewPager2(binding.saleViewPager)

        saleRecyclerAdapter = SaleRecyclerAdapter(requireContext())
        binding.saleRv.adapter = saleRecyclerAdapter

        categoryRecyclerAdapter = CategoryRecyclerAdapter(requireContext())
        binding.categoryRv.adapter = categoryRecyclerAdapter

        return view
    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init view model
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java);
        viewModel.getItems()
        viewModel.getSaleItems()
        viewModel.getAllCategories()

        //search bar
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
                val action = HomeFragmentDirections.actionHomeFragmentToSearchSucceedFragment(query = text.toString() ,
                    searchStatus = SearchSucceedFragment.SearchStatus.QUERY)
                view.findNavController().navigate(action)
            }
        });
        binding.searchBar.lastSuggestions = lastSearches;



        //listeners
        binding.notificationIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_notificationFragment);
        }

        binding.favoriteIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_favoriteFragment);
        }

        binding.moreCategoryTv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_listCategoryFragment);
        }

        binding.saleSeeMoreTv.setOnClickListener {
            onClick(it)
        }


        //recommended adapter item listener
        recommendedRecyclerAdapter.itemClickListener = object: RecommendedRecyclerAdapter.ItemClickListener{
            override fun onClick(view: View, item: ProductResponse.Item) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
                view.findNavController().navigate(action)
            }

        }

        binding.saleViewPager.registerOnPageChangeCallback( object :
            ViewPager2.OnPageChangeCallback()  {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable,3000)
            }
        })



        navController = Navigation.findNavController(view)
        view.findViewById<TextView>(R.id.moreCategory_tv).setOnClickListener(this)
        binding.moreCategoryTv.setOnClickListener(this)


    }

    private fun subscribeToLiveData() {
        // get all data to Recomend RecyclerView
        viewModel.itemsLiveData.observe(requireActivity(), Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    binding.recommendedProgress.showShimmerAdapter()
                    binding.recommendedRv.visibility = View.GONE

                }

                Resource.Status.ERROR ->{
                    binding.recommendedProgress.hideShimmerAdapter()
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }

                Resource.Status.SUCCESS->{
                    it.data.let {
                        binding.recommendedProgress.hideShimmerAdapter()
                        binding.recommendedRv.visibility = View.VISIBLE
                        recommendedRecyclerAdapter.changeData(it!!)
                    }
                }
            }
        })


        // get offer data to Sale RecyclerView and ViewPager
        viewModel.saleItemsLiveData.observe(requireActivity(), Observer {

            when(it.status){
                Resource.Status.LOADING -> {
                   // binding.viewPagerProgress.visibility = View.VISIBLE
                    binding.saleProgress.showShimmerAdapter()
                    binding.viewPagerProgress.showShimmerAdapter()

                    binding.saleRv.visibility = View.INVISIBLE
                    binding.saleViewPager.visibility= View.GONE
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                   // binding.viewPagerProgress.visibility = View.GONE
                    binding.saleProgress.hideShimmerAdapter()
                    binding.viewPagerProgress.hideShimmerAdapter()

                    binding.saleRv.visibility=View.VISIBLE
                    binding.saleViewPager.visibility=View.VISIBLE
                }

                Resource.Status.SUCCESS-> {
                    it.data.let {
                       // binding.viewPagerProgress.visibility = View.GONE
                        binding.saleProgress.hideShimmerAdapter()
                        binding.viewPagerProgress.hideShimmerAdapter()

                        binding.saleRv.visibility=View.VISIBLE
                        binding.saleViewPager.visibility=View.VISIBLE

                        viewPagerAdapter.changeData(it!!)
                        saleRecyclerAdapter.changeData(it!!)
                        binding.dotsIndicator.setViewPager2(binding.saleViewPager)
                    }
                }
            }
        })

        // get caegory to category RecyclerView and ViewPager
        viewModel.categoryLiveData.observe(requireActivity(), Observer {
            when(it.status){

                Resource.Status.LOADING ->{
                    Log.e("sssss","Loading........")
                    binding.categoryProgress.showShimmerAdapter()
                    binding.categoryRv.visibility=View.GONE
                }

                Resource.Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    binding.categoryProgress.hideShimmerAdapter()
                    binding.categoryRv.visibility=View.GONE
                }

                Resource.Status.SUCCESS->{
                    it.data.let {
                        binding.categoryProgress.hideShimmerAdapter()
                        binding.categoryRv.visibility=View.VISIBLE
                        categoryRecyclerAdapter.changeData(it!!)

                    }
                }
            }
        })

    }

    private val sliderRunnable:Runnable = Runnable {
        kotlin.run {
            binding.saleViewPager.currentItem = binding.saleViewPager.currentItem + 1

            if (binding.saleViewPager.currentItem == saleData.size-1) {
                saleData.shuffle()
                viewPagerAdapter.changeData(saleData)
                binding.saleViewPager.currentItem = 0
            }

        }
    }



    override fun onClick(v: View) {
        when(v){
            binding.moreCategoryTv ->{
                val action = HomeFragmentDirections.actionHomeFragmentToListCategoryFragment()
                view?.findNavController()?.navigate(action)
                Toast.makeText(requireContext(),"aaaaaa",Toast.LENGTH_SHORT).show()
            }

            binding.saleSeeMoreTv ->{
                val action = HomeFragmentDirections.actionHomeFragmentToOfferTypeFragment("flash sale")
                view?.findNavController()?.navigate(action)
                Toast.makeText(requireContext(),"aaaaaa",Toast.LENGTH_SHORT).show()
            }
        }
    }




}