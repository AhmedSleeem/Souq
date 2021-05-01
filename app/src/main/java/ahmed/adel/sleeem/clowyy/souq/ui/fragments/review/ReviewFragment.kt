package ahmed.adel.sleeem.clowyy.souq.ui.fragments.review

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.writeReview.ReviewStarAdapter
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentReviewBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ReviewItem
import ahmed.adel.sleeem.clowyy.souq.pojo.StarItem
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class ReviewFragment : Fragment(), View.OnClickListener {

    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var starAdapter: ReviewStarAdapter
    private var _binding: FragmentReviewBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor()
        initStarRecyclerView()
        initReviewRecyclerView()

        binding.writeReviewButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
             binding.writeReviewButton ->{
                  val action = ReviewFragmentDirections.actionReviewFragmentToWriteReviewFragment()
                  view?.findNavController()?.navigate(action)
              }
        }
    }

    private fun initStarRecyclerView() {
        starAdapter = ReviewStarAdapter { _, _, _ -> }

        var star1 = StarItem(1)
        var star2 = StarItem(2)
        var star3 = StarItem(3)
        var star4 = StarItem(4)
        var star5 = StarItem(5)
        var starList = mutableListOf<StarItem>(star1, star2, star3, star4, star5)
        starAdapter.swapData(starList)
        binding.reviewStarRecyclerView.apply {
            adapter = starAdapter
        }
    }

    private fun initReviewRecyclerView() {
        reviewAdapter = ReviewAdapter { _, _, _ -> }

        var star1 = ReviewItem()
        var star2 = ReviewItem()

        var starList = mutableListOf<ReviewItem>(star1, star2)
        reviewAdapter.swapData(starList)
        binding.reviewRecyclerView.apply {
            adapter = reviewAdapter
        }
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