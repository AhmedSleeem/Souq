package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentDetailsBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ReviewResponse
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteItem
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteViewModelRoom
import ahmed.adel.sleeem.clowyy.souq.room.cart.Cart
import ahmed.adel.sleeem.clowyy.souq.room.cart.CartViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.ColorRecylerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.RecommendedDetailsRA
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.SizeRecyclerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter.ViewPagerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.recommended.RecommendedAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.OrderDetailsViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.OrderViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.review.adapter.ReviewAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.CartRoom
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.OnBadgeChangeListener
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
import com.bumptech.glide.Glide
import kotlinx.coroutines.*


class DetailsFragment : Fragment() {
    companion object{
        var badgeCount = 0
        var setOnCountChangeListener : OnBadgeChangeListener? = null
    }

    private lateinit var cartViewModel: CartViewModel
    private lateinit var listImg: MutableList<String>
    private var saleData = arrayListOf<ProductResponse.Item>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var selectSizeAdapter: SizeRecyclerAdapter
    private lateinit var colorAdapter: ColorRecylerAdapter
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var recommendRecyclerAdapter: RecommendedDetailsRA
    private lateinit var reviewRecyclerAdapter: ReviewAdapter
    private lateinit var viewModel: DetailsViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    private  var item: ProductResponse.Item? = null

    private lateinit var orderViewModel : OrderDetailsViewModel

    private lateinit var  favoriteViewModel : FavouriteViewModelRoom



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

        if (args.itemData !=null) {
            item = args.itemData
            Log.e("TAG", "onCreateView: id =>>"+item!!.id )
            binding.appBar.title = item!!.title
            binding.productNameTv.text = item!!.title
            binding.ratingBar.rating = (item!!.rating / 2.0f).toFloat()
            binding.price.text = item!!.price.toString() + " Egp"
            binding.descriptionTv.text = item!!.description
            binding.companyNameTv.text = item!!.companyName
            binding.brandTv.text = item!!.brand
        }else {
            orderViewModel = ViewModelProvider(this).get(OrderDetailsViewModel::class.java);
            val itemsById = orderViewModel.getItemsById(args.itemId!!)
        }

        //View Models

        favoriteViewModel = ViewModelProvider(this).get(FavouriteViewModelRoom::class.java);

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java);

        recommendRecyclerAdapter = RecommendedDetailsRA(requireContext())
        binding.recommend.adapter = recommendRecyclerAdapter



        viewPagerAdapter = ViewPagerAdapter(requireContext())
        binding.saleViewPager1.adapter = viewPagerAdapter
        binding.dotsIndicator1.setViewPager2(binding.saleViewPager1)
        listImg = mutableListOf(item!!.image)
        if (item!!.sale != null) {
            if (item!!!!.sale!!.image != null) {
                viewPagerAdapter.changeData(item!!.sale!!.image!!)
            } else {
                viewPagerAdapter.changeData(listImg)
            }
        } else {
            viewPagerAdapter.changeData(listImg)
        }


        if (item!!.size == null) {
            binding.sizeRv.visibility = View.GONE
            binding.selectSizeTxt.visibility = View.GONE
        } else {
            selectSizeAdapter = SizeRecyclerAdapter(item!!.size)
            binding.sizeRv.adapter = selectSizeAdapter
            binding.sizeRv.visibility = View.VISIBLE
        }

        if (item!!.color == null) {
            binding.colorRv.visibility = View.GONE
            binding.selectColorTxt.visibility = View.GONE
        } else {
            binding.colorRv.visibility = View.VISIBLE
            colorAdapter = ColorRecylerAdapter(item!!.color, requireContext())
            binding.colorRv.adapter = colorAdapter
        }

        val user = LoginUtils.getInstance(requireContext())!!.userInfo()
        CoroutineScope(Dispatchers.IO).launch{
            val selectItem = favoriteViewModel.selectItem(user._id.toString(), item!!.id.toString());

            if (selectItem) {
                withContext(Dispatchers.Main) {
                    binding.favoritBtnRed.visibility = View.VISIBLE;
                    binding.favoritBtn.visibility = View.GONE;
                }
            }
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
        viewModel.getItemsByCategory(item!!.category.name)
        viewModel.getReviewsByProductId(item!!.id.toString())

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        binding.addReview.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToWriteReviewFragment(null,
                false,item!!.id.toString())
            it.findNavController().navigate(action)
        }


        binding.favoritBtn.setOnClickListener{
            val user = LoginUtils.getInstance(requireContext())!!.userInfo()
            val fav = FavouriteItem(0,item!!.id.toString(),user._id,
                item!!.title,item!!.image,
                item!!.rating.toFloat(),item!!.price,item!!.sale!!.amount);
            favoriteViewModel.addItem(fav);

            binding.favoritBtnRed.visibility = View.VISIBLE;
            binding.favoritBtn.visibility = View.GONE;

        }

        binding.favoritBtnRed.setOnClickListener{
            val user = LoginUtils.getInstance(requireContext())!!.userInfo()

            favoriteViewModel.deleteItem(user._id!!,item!!.id.toString());

            binding.favoritBtnRed.visibility = View.GONE;
            binding.favoritBtn.visibility = View.VISIBLE;

        }


        recommendRecyclerAdapter.itemClickListener =
            object : RecommendedDetailsRA.ItemClickListener {
                override fun onClick(view: View, item: ProductResponse.Item) {
                    val action = DetailsFragmentDirections.actionDetailsFragmentSelf(item , null)
                    view.findNavController().navigate(action)
                }
            }

        binding.morweReviews.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToReviewFragment(item!!.id.toString())
           view.findNavController().navigate(action)
        }


        // add to cart action
        binding.addToCartBtn.setOnClickListener{
            if((item!!.color != null && item!!.color.size !=0) && (item!!.size != null && item!!.size.size !=0 )) {

                if (item!!.selectedColor != null && item!!.selectedSize != null) {
                    item!!.countOfSelectedItem = 1


                    val user = LoginUtils.getInstance(requireContext())!!.userInfo()


                    val car = Cart(0,item!!.id,item!!.title,
                        item!!.countOfSelectedItem,item!!.price,item!!.image,user._id!!
                        ,item!!.selectedColor!!,item!!.selectedSize!!,item!!.companyName);

                    var count : Long = 0
                    CoroutineScope(Dispatchers.IO).launch {
                      count = cartViewModel.insert(car);
                        withContext(Dispatchers.Main){
                            if (count > 0){
                                badgeCount++
                                setOnCountChangeListener?.onChange(badgeCount)
                            }
                        }
                    }

//                    CartRoom.cartList.add(item!!)

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please choose colore and size",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                item!!.countOfSelectedItem = 1
//                CartRoom.cartList.add(item!!)

                val user = LoginUtils.getInstance(requireContext())!!.userInfo()
                val car = Cart(0,item!!.id,item!!.title,
                    item!!.countOfSelectedItem,item!!.price,item!!.image,user._id!!
                ,"","",item!!.companyName);

                var count : Long = 0
                CoroutineScope(Dispatchers.IO).launch {
                    count = cartViewModel.insert(car);
                    withContext(Dispatchers.Main) {
                        if (count > 0) {
                            badgeCount++
                            setOnCountChangeListener?.onChange(badgeCount)
                        }
                    }
                }

            }


        }

        // size clickListner
        if(item!!.size != null) {
            selectSizeAdapter.setOnItemClickListner = object : SizeRecyclerAdapter.ClckListner {
                override fun clickListner(itemSize: String) {
                    item!!.selectedSize = itemSize
                }

            }
        }

        //color clickListner
        if(item!!.color != null) {
            colorAdapter.setOnItemClickListner = object : ColorRecylerAdapter.ClckListner {
                override fun clickListner(itemColor: String) {
                    item!!.selectedColor = itemColor
                }

            }
        }


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
                    }
                }
            }
        })

        viewModel.reviewsLiveData.observe(viewLifecycleOwner , Observer {
            when(it.status){
                Resource.Status.LOADING ->{

                }
                Resource.Status.SUCCESS ->{
                    changeReviewUi(it.data!!)
                }
                Resource.Status.ERROR ->{
                    binding.rateView.visibility=View.GONE
                    binding.noReviewViews.visibility=View.VISIBLE
                }
            }
        })

        viewModel.userLiveData.observe(viewLifecycleOwner , Observer {
            when(it.status){
                Resource.Status.LOADING ->{

                }
                Resource.Status.SUCCESS ->{
                    Glide.with(requireActivity())
                        .load(it.data!!.profileImage)
                        .into(binding.profileReviewImageView)
                    binding.usernameReviewTextView.text = it.data!!.name
                }
            }
        })

        viewModel.reviewAvgLiveData.observe(viewLifecycleOwner, Observer {
            binding.ratingBar.rating = it.second
            binding.ratingBar1.rating = it.second
            binding.rate.text = String.format("%.2f",it.second)
        })
    }

    private fun changeReviewUi(data: ReviewResponse) {
        val reviewItem = data[0]
        binding.countOfReating.text = " (${data.size} Reviews)"
        viewModel.getUserById(reviewItem.userId)
        binding.reviewTextView.text = reviewItem.description
        binding.ratingReviewBar.rating = reviewItem.rating.toFloat()




    }
}