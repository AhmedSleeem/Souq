package ahmed.adel.sleeem.clowyy.souq.ui.fragments.review

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentReviewBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.request.ReviewRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ReviewResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.review.adapter.ReviewAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.review.adapter.ReviewStarAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar

class ReviewFragment : Fragment(), View.OnClickListener {

    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var starAdapter: ReviewStarAdapter
    lateinit var binding: FragmentReviewBinding
    private val args by navArgs<ReviewFragmentArgs>()
    private lateinit var viewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)
        subscribeToLiveData()
        viewModel.getReviewsByProductId(args.productId!!)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.deletedReview != null)
            showSnackBar(view)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        reviewAdapter = ReviewAdapter(requireContext())
        binding.reviewRecyclerView.adapter = reviewAdapter

        starAdapter = ReviewStarAdapter()
        binding.reviewStarRecyclerView.adapter = starAdapter

        starAdapter.setOnItemClick = object : ReviewStarAdapter.OnItemClick {
            override fun onClick(position: Int) {
                viewModel.getReviewByRate(args.productId.toString(), rate = position)
            }
        }

        reviewAdapter.setOnItemClick = object : ReviewAdapter.OnItemClick {
            override fun onClick(item: ReviewResponse.Item) {
                if (viewModel.getCurrentUserId() == item.userId) {
                    val action = ReviewFragmentDirections.actionReviewFragmentToWriteReviewFragment(
                        item,
                        true,
                        args.productId
                    )
                    view.findNavController().navigate(action)
                }
            }
        }

        setStatusBarColor()
        binding.writeReviewButton.setOnClickListener(this)
    }

    private fun showSnackBar(view: View) {
        Snackbar.make(view, "Review Deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") { _ ->
                viewModel.addReview(
                    ReviewRequest(
                        args.deletedReview!!.description,
                        args.deletedReview!!.itemId,
                        args.deletedReview!!.rating,
                        args.deletedReview!!.userId
                    ), args.productId!!
                )
            }
            .show()
    }

    private fun subscribeToLiveData() {
        viewModel.reviewsLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.reviewRecyclerView.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    reviewAdapter.changeData(it.data!!, true)
                    binding.progress.visibility = View.GONE
                    binding.reviewRecyclerView.visibility = View.VISIBLE
                }

                Resource.Status.ERROR -> {
                    val errorMessage = when (it.message?.toInt()) {
                        400 -> "No Internet Connection"
                        else -> "Server Interrupted"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()

                    binding.progress.visibility = View.GONE
                    binding.reviewRecyclerView.visibility = View.VISIBLE
                    reviewAdapter.changeData(ReviewResponse(), true)
                }
            }
        })
    }

    override fun onClick(v: View) {
        when (v) {
            binding.writeReviewButton -> {
                val action = ReviewFragmentDirections.actionReviewFragmentToWriteReviewFragment(
                    null,
                    false,
                    args.productId
                )
                view?.findNavController()?.navigate(action)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setStatusBarColor() {
        val window: Window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }


}