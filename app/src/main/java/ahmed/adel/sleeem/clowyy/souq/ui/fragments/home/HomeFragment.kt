package ahmed.adel.sleeem.clowyy.souq.ui.home_fragment

import ahmed.adel.sleeem.clowyy.souq.*
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import ahmed.adel.sleeem.clowyy.souq.pojo.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.HomeViewModel
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

    val images = intArrayOf(
        R.drawable.i1,
        R.drawable.i2,
        R.drawable.i3,
        R.drawable.i1,
        R.drawable.i2,
        R.drawable.i3
    )
    val categories = listOf<ExplorerItem>(
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

        var list = mutableListOf<SaleItem>(
            SaleItem(R.drawable.bag2,"FS - Nike Air Max 270 React...","24% Off",299.34f,534.34f),
            SaleItem(R.drawable.shoes,"FS - Nike Air Max 270 React...","24% Off",299.34f,534.34f),
            SaleItem(R.drawable.shoes2,"FS - Nike Air Max 270 React...","24% Off",299.34f,534.34f),
            SaleItem(R.drawable.womem_bag,"FS - Nike Air Max 270 React...","24% Off",299.34f,534.34f),
            SaleItem(R.drawable.shoes,"FS - Nike Air Max 270 React...","24% Off",299.34f,534.34f),
            SaleItem(R.drawable.bag2,"FS - Nike Air Max 270 React...","24% Off",299.34f,534.34f),
            SaleItem(R.drawable.shoes2,"FS - Nike Air Max 270 React...","24% Off",299.34f,534.34f),
        )




        saleRecyclerAdapter = SaleRecyclerAdapter(items = list)
        binding.saleRv.adapter = saleRecyclerAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java);
        viewModel.getItemsByCategory("electronics")
        subscribeToLiveData()



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


//        recommendedRecyclerAdapter.itemClickListner = object: RecommendedRecyclerAdapter.ItemClickListner{
//            override fun onClick(view: View) {
//               Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailsFragment)
//            }
//
//        }

        viewPagerAdapter = SaleViewPagerAdapter(images = images)
        binding.saleViewPager.adapter = viewPagerAdapter
        binding.dotsIndicator.setViewPager2(binding.saleViewPager)






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
                    binding.progress.visibility = View.VISIBLE
                }
                Resource.Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    binding.progress.visibility = View.GONE
                }
                Resource.Status.SUCCESS->{
                    it.data.let {
                        recommendedRecyclerAdapter = RecommendedRecyclerAdapter(it!!,requireActivity())
                        binding.recommended.adapter = recommendedRecyclerAdapter
                        binding.progress.visibility = View.GONE
                        //Log.e("sssss", it!!.get(0).title )
                    }
                }
            }
        })
        viewModel.filterLiveData.observe(requireActivity(), Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Log.e("sssss","Loading........")
                    //binding.progress.visibility = View.VISIBLE
                }
                Resource.Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                   // binding.progress.visibility = View.GONE
                }
                Resource.Status.SUCCESS->{
                    it.data.let {
//                       recommendedRecyclerAdapter = RecommendedRecyclerAdapter(it!!,requireActivity())
//                         binding.recommended.adapter = recommendedRecyclerAdapter
//                        binding.progress.visibility = View.GONE
                        //Log.e("sssss", it!!.get(0).title )
                        Log.e("sssss",it!!.get(0).category)
                    }
                }
            }
        })
    }

    private val sliderRunnable:Runnable = Runnable {
        kotlin.run {
            binding.saleViewPager.currentItem = binding.saleViewPager.currentItem + 1

            if (binding.saleViewPager.currentItem == images.size-1) {
                images.shuffle()
                viewPagerAdapter.changeData(images)
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
                val action = HomeFragmentDirections.actionHomeFragmentToOfferTypeFragment()
                view?.findNavController()?.navigate(action)
                Toast.makeText(requireContext(),"aaaaaa",Toast.LENGTH_SHORT).show()
            }
        }
    }




}