package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home

import ahmed.adel.sleeem.clowyy.souq.*
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
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


class HomeFragment : Fragment() , View.OnClickListener {

    var navController : NavController? = null

    private var saleData = arrayListOf<ProductResponse.Item>()
    private val categories = listOf<ExplorerItem>(
        ExplorerItem( R.drawable.ic_dress),
        ExplorerItem( R.drawable.ic_man_bag),
        ExplorerItem( R.drawable.ic_woman_bag),
        ExplorerItem( R.drawable.ic_man_bag),
        ExplorerItem( R.drawable.ic_dress)
    )
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
        binding.recommended.adapter = recommendedRecyclerAdapter

        viewPagerAdapter = SaleViewPagerAdapter(requireContext())
        binding.saleViewPager.adapter = viewPagerAdapter
        binding.dotsIndicator.setViewPager2(binding.saleViewPager)

        saleRecyclerAdapter = SaleRecyclerAdapter(requireContext())
        binding.saleRv.adapter = saleRecyclerAdapter

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

        binding.saleViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback()  {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable,3000)
            }
        })

        categoryRecyclerAdapter = CategoryRecyclerAdapter(categories)
        binding.categoryRv.adapter = categoryRecyclerAdapter

        navController = Navigation.findNavController(view)
        view.findViewById<TextView>(R.id.moreCategory_tv).setOnClickListener(this)
        binding.moreCategoryTv.setOnClickListener(this)


    }

    private fun subscribeToLiveData() {
        viewModel.itemsLiveData.observe(requireActivity(), Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Log.e("sssss","Loading........")
                    binding.recommendedProgress.visibility = View.VISIBLE
                    binding.viewPagerProgress.visibility = View.VISIBLE
                    binding.saleProgress.visibility = View.VISIBLE
                }
                Resource.Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    binding.recommendedProgress.visibility = View.GONE
                    binding.viewPagerProgress.visibility = View.GONE
                    binding.saleProgress.visibility = View.GONE
                }
                Resource.Status.SUCCESS->{
                    it.data.let {
                        recommendedRecyclerAdapter.changeData(it!!)
                        binding.recommendedProgress.visibility = View.GONE
                        binding.viewPagerProgress.visibility = View.GONE
                        binding.saleProgress.visibility = View.GONE
                        Log.e("sssss", it!!.get(0).title)
                        Log.e("TAG", "aboutaha: " + it.size)
                        for (item in it) {
                            if (item.sale != null)
                                saleData.add(item)
                        }
                        Log.e("TAG", "aboutaha: " + saleData.size)
                        viewPagerAdapter.changeData(saleData)
                        saleRecyclerAdapter.changeData(saleData)
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