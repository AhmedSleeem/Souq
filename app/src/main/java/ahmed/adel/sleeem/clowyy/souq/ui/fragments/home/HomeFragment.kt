package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home

import ahmed.adel.sleeem.clowyy.souq.*
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.CategoryRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.RecommendedRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.SaleRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.SaleViewPagerAdapter
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2


class HomeFragment : Fragment(),View.OnClickListener {

    val images = intArrayOf(
        R.drawable.i1,
        R.drawable.i2,
        R.drawable.i3,
        R.drawable.i1,
        R.drawable.i2,
        R.drawable.i3
    )
    val categories = intArrayOf(
        R.drawable.ic_dress,
        R.drawable.ic_man_bag,
        R.drawable.ic_woman_bag,
        R.drawable.ic_shirt,
        R.drawable.ic_man_bag,
        R.drawable.ic_dress
    )
    private lateinit var viewPagerAdapter: SaleViewPagerAdapter
    private lateinit var saleRecyclerAdapter: SaleRecyclerAdapter
    private lateinit var recommendedRecyclerAdapter: RecommendedRecyclerAdapter
    private lateinit var categoryRecyclerAdapter: CategoryRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private val sliderHandler = Handler();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewPagerAdapter = SaleViewPagerAdapter(images = images)
        binding.saleViewPager.adapter = viewPagerAdapter
        binding.dotsIndicator.setViewPager2(binding.saleViewPager)

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

        recommendedRecyclerAdapter = RecommendedRecyclerAdapter(items = list)
        binding.recommendedRv.adapter = recommendedRecyclerAdapter

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

        binding.moreCategoryTv.setOnClickListener(this)


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
                val action = HomeFragmentDirections.actionHomeFragmentToReviewFragment()
                view?.findNavController()?.navigate(action)
                Toast.makeText(requireContext(),"aaaaaa",Toast.LENGTH_SHORT).show()
            }
        }
    }


}