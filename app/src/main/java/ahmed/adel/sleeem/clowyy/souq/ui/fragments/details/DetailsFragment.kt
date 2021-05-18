package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentDetailsBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.ColorRecylerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.SizeRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.ViewPagerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.RecommendedRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation


class DetailsFragment : Fragment() {

    val sizeArr = arrayOf(
       "6", "6.5" , "7" , "7.5" , "8" , "8.5" , "9"
    )


    val images = intArrayOf(
        R.drawable.i1,
        R.drawable.i2,
        R.drawable.i3,
        R.drawable.i1,
        R.drawable.i2,
        R.drawable.i3
    )

    val colors = intArrayOf(
        R.color.black,
        R.color.red,
        R.color.overlayGray,
        R.color.primaryBlue,
        R.color.purple_200,
        R.color.design_default_color_primary_variant,
        R.color.purple_500,
    )


    private lateinit var viewPager : ViewPagerAdapter
    private lateinit var selectSizeAdapter : SizeRecyclerAdapter
    private lateinit var colorAdapter : ColorRecylerAdapter
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var recommendRecyclerAdapter : RecommendedRecyclerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
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
        recommendRecyclerAdapter = RecommendedRecyclerAdapter(items = list)
        binding.recommend.adapter = recommendRecyclerAdapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        recommendRecyclerAdapter.itemClickListner = object: RecommendedRecyclerAdapter.ItemClickListner {
            override fun onClick(view: View) {
                Navigation.findNavController(view)
                    .navigate(R.id.action_detailsFragment_self)
            }
        }

        binding.morweReviews.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailsFragment_to_reviewFragment);
        }


        viewPager = ViewPagerAdapter(images = images)
        binding.saleViewPager1.adapter = viewPager
        binding.dotsIndicator1.setViewPager2(binding.saleViewPager1)

        selectSizeAdapter = SizeRecyclerAdapter(sizeArr)
        binding.sizeRv.adapter = selectSizeAdapter

        colorAdapter = ColorRecylerAdapter(colors)
        binding.colorRv.adapter = colorAdapter



    }
}