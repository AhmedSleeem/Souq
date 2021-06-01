package ahmed.adel.sleeem.clowyy.souq.ui.fragments.review

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentWriteReviewBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.FullUserInfo
import ahmed.adel.sleeem.clowyy.souq.pojo.request.ReviewRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.request.UserRequist
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs


class WriteReviewFragment : Fragment() {
    private var _binding: FragmentWriteReviewBinding? = null
    lateinit var viewModel: ReviewViewModel
    lateinit var userInfo: FullUserInfo
    val args by navArgs<WriteReviewFragmentArgs>()

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor()
        userInfo = LoginUtils.getInstance(requireActivity())!!.userInfo()
        viewModel = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)
        subscribeToLiveData()


        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.rateNumTv.text = rating.toInt().toString()+"/5"
        }

        binding.writeReviewButton.setOnClickListener {
            val description = binding.reviewDescriptionEd.text
            if (description.isNotEmpty() && description.isNotBlank()){
                viewModel.addReview(ReviewRequest(
                        description.toString(),
                        args.productId!!,
                        binding.ratingBar.numStars.toDouble()*2.0,
                        this.userInfo._id!!
                        ))
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.addReviewLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status){
                Resource.Status.SUCCESS ->{
                    Toast.makeText(requireContext(), "review added ☺", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setStatusBarColor() {
        val window: Window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}