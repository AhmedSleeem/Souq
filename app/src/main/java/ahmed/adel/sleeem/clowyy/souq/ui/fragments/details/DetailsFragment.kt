package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentDetailsBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.ColorRecylerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.SizeRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.ViewPagerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.RecommendedRecyclerAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs


class DetailsFragment : Fragment() {

    val sizeArr = arrayOf(
        "6", "6.5", "7", "7.5", "8", "8.5", "9"
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

    private var saleData = arrayListOf<ProductResponse.Item>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var selectSizeAdapter: SizeRecyclerAdapter
    private lateinit var colorAdapter: ColorRecylerAdapter
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var recommendRecyclerAdapter: RecommendedRecyclerAdapter
    private lateinit var viewModel: DetailsViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var item: ProductResponse.Item

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

        item = args.itemData
        binding.appBar.title = item.title
        binding.productNameTv.text = item.title
        binding.ratingBar.rating = (item.rating/2.0f)
        binding.price.text = item.price.toString()+" Egp"
        binding.styleDetails2.text = item.description

        recommendRecyclerAdapter = RecommendedRecyclerAdapter(requireContext())
        binding.recommend.adapter = recommendRecyclerAdapter

        viewPagerAdapter = ViewPagerAdapter(requireContext())
        binding.saleViewPager1.adapter = viewPagerAdapter
        binding.dotsIndicator1.setViewPager2(binding.saleViewPager1)

        return view
    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init view model
        viewModel = ViewModelProvider(requireActivity()).get(DetailsViewModel::class.java);
        viewModel.getItemsByCategory("women's clothing")

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        recommendRecyclerAdapter.itemClickListener =
            object : RecommendedRecyclerAdapter.ItemClickListener {
                override fun onClick(view: View, item: ProductResponse.Item) {
                    val action = DetailsFragmentDirections.actionDetailsFragmentSelf(item)
                    view.findNavController().navigate(action)
                }
            }

        binding.morweReviews.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_detailsFragment_to_reviewFragment);
        }



        selectSizeAdapter = SizeRecyclerAdapter(sizeArr)
        binding.sizeRv.adapter = selectSizeAdapter

        colorAdapter = ColorRecylerAdapter(colors)
        binding.colorRv.adapter = colorAdapter

    }

    private fun subscribeToLiveData() {
        viewModel.itemsLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                    binding.viewPagerProgress.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.viewPagerProgress.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        binding.viewPagerProgress.visibility = View.GONE
                        viewPagerAdapter.changeData(it!!)
                        Log.e("sssss", it!!.get(0).title)
                    }
                }
            }
        })

        viewModel.filterLiveData.observe(requireActivity(), Observer {
            when (it.status) {

                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                    binding.recommendedProgress.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.recommendedProgress.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        binding.recommendedProgress.visibility = View.GONE
                        recommendRecyclerAdapter.changeData(it!!)
                        saleData = it
                    }
                }
            }
        })
    }
}