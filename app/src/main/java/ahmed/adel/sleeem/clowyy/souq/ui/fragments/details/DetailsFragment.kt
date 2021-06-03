package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentDetailsBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteItem
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteViewModelRoom
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.ColorRecylerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.SizeRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.ViewPagerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter.RecommendedRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
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


class DetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var listImg: MutableList<String>
    private var saleData = arrayListOf<ProductResponse.Item>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var selectSizeAdapter: SizeRecyclerAdapter
    private lateinit var colorAdapter: ColorRecylerAdapter
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var recommendRecyclerAdapter: RecommendedRecyclerAdapter
    private lateinit var viewModel: DetailsViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var item: ProductResponse.Item
    private lateinit var favItem: FavouriteItem
    private lateinit var favouriteViewModelRoom: FavouriteViewModelRoom


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

        favouriteViewModelRoom = ViewModelProvider(this).get(FavouriteViewModelRoom::class.java)


        item = args.itemData
        binding.appBar.title = item.title
        binding.productNameTv.text = item.title
        binding.ratingBar.rating = (item.rating / 2.0f)
        binding.price.text = item.price.toString() + " Egp"
        binding.descriptionTv.text = item.description
        binding.companyNameTv.text = item.companyName
        binding.brandTv.text = item.brand

        recommendRecyclerAdapter = RecommendedRecyclerAdapter(requireContext())
        binding.recommend.adapter = recommendRecyclerAdapter

        viewPagerAdapter = ViewPagerAdapter(requireContext())
        binding.saleViewPager1.adapter = viewPagerAdapter
        binding.dotsIndicator1.setViewPager2(binding.saleViewPager1)
        listImg = mutableListOf(item.image)
        if (item.sale != null) {
            if (item.sale!!.image != null) {
                viewPagerAdapter.changeData(item.sale!!.image!!)
            } else {
                viewPagerAdapter.changeData(listImg)
            }
        } else {
            viewPagerAdapter.changeData(listImg)
        }


        if (item.size == null) {
            binding.sizeRv.visibility = View.GONE
            binding.selectSizeTxt.visibility = View.GONE
        } else {
            selectSizeAdapter = SizeRecyclerAdapter(item.size)
            binding.sizeRv.adapter = selectSizeAdapter
            binding.sizeRv.visibility = View.VISIBLE
        }

        if (item.color == null) {
            binding.colorRv.visibility = View.GONE
            binding.selectColorTxt.visibility = View.GONE
        } else {
            binding.colorRv.visibility = View.VISIBLE
            colorAdapter = ColorRecylerAdapter(item.color, requireContext())
            binding.colorRv.adapter = colorAdapter
        }

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
        viewModel.getItemsByCategory(item.category.name)

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
                .navigate(R.id.action_detailsFragment_to_reviewFragment)
        }

        binding.favoritBtn.setOnClickListener(this)
    }

    private fun subscribeToLiveData() {

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

    private fun insertDataToDatabase() {

        val productName = item.title
        val itemId = item.id.toString()
        val userId = LoginUtils.getInstance(requireContext())!!.userInfo()._id
        val productImage = item.image
        val rating = item.rating
        val price = item.price
        val offer = item.sale.amount


        // Create User Object
        val item = FavouriteItem(
            0,
            itemId,
            userId,
            productName,
            productImage,
            rating,
            price,
            offer
        )

        // Add Data to Database
        favouriteViewModelRoom.addItem(item)
        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.favoritBtn -> {
                insertDataToDatabase()
            }
        }
    }
}