package ahmed.adel.sleeem.clowyy.souq.ui.fragments.review

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentWriteReviewBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.FullUserInfo
import ahmed.adel.sleeem.clowyy.souq.pojo.request.DeleteReviewRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.request.ModifyReviewRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.request.ReviewRequest
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.ui.navigateUp


class WriteReviewFragment : Fragment() {
    private var _binding: FragmentWriteReviewBinding? = null
    lateinit var viewModel: WriteReviewViewModel
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
        viewModel = ViewModelProvider(requireActivity()).get(WriteReviewViewModel::class.java)




        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        if (args.modify && args.reviewResponse!=null){
            updateUi()

        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.rateNumTv.text = rating.toInt().toString()+"/5"
        }

        binding.writeReviewButton.setOnClickListener {
            val description = binding.reviewDescriptionEd.text

            if (description.isNotEmpty() && description.isNotBlank()){
                val review=ReviewRequest(
                    description.toString(),
                    args.productId!!,
                    binding.ratingBar.rating*2.0 ,
                    this.userInfo._id!!
                )
                viewModel.addReview(review)
                subscribeToLiveData(it)
            }
        }



        binding.uodateReviewButton.setOnClickListener {
            val desc=binding.reviewDescriptionEd.text.toString()
            if (desc.isNotBlank() && desc.isNotEmpty()){

            var model = ModifyReviewRequest(desc,
                (binding.ratingBar.rating.toDouble()*2),
                args.reviewResponse!!._id
            )
            viewModel.modifyReview(model)
                subscribeToLiveData(it)
            }else
                binding.reviewDescriptionEd.setError("description is required!!")
        }

        binding.removeReviewButton.setOnClickListener {
            viewModel.deleteReview(DeleteReviewRequest((args.reviewResponse!!._id)))
            subscribeToLiveData(it)
        }
    }

    private fun updateUi() {
        binding.ratingBar.rating = (args.reviewResponse!!.rating/2).toFloat()
        binding.reviewDescriptionEd.setText(args.reviewResponse!!.description)
        binding.rateNumTv.text = (args.reviewResponse!!.rating/2).toInt().toString() + "/5"
        binding.writeReviewButton.visibility=View.GONE
        binding.removeReviewButton.visibility=View.VISIBLE
        binding.uodateReviewButton.visibility=View.VISIBLE
    }

    private fun subscribeToLiveData(view: View) {
        viewModel.addReviewLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status){
                Resource.Status.SUCCESS ->{
                    if (view == binding.writeReviewButton){
                        val action = WriteReviewFragmentDirections.actionWriteReviewFragmentToReviewFragment(args.productId,null)
                        view.findNavController().navigate(action)
                    }

                }
                Resource.Status.ERROR ->{
                    val errorMessage = when (it.message?.toInt()) {
                        400 -> "No Internet Connection"
                        else -> "Server Interrupted"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.modifyReviewLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status){
                Resource.Status.SUCCESS ->{
                    if (view == binding.uodateReviewButton){
                        val action = WriteReviewFragmentDirections.actionWriteReviewFragmentToReviewFragment(args.productId,null)
                        view.findNavController().navigate(action)
                    }
                }
                Resource.Status.ERROR ->{
                    val errorMessage = when (it.message?.toInt()) {
                        400 -> "No Internet Connection"
                        else -> "Server Interrupted"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.deleteReviewLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status){
                Resource.Status.SUCCESS ->{
                    if (view == binding.removeReviewButton){
                        val action = WriteReviewFragmentDirections.actionWriteReviewFragmentToReviewFragment(args.productId,args.reviewResponse)
                        view.findNavController().navigate(action)
                    }
                }
                Resource.Status.ERROR ->{
                    val errorMessage = when (it.message?.toInt()) {
                        400 -> "No Internet Connection"
                        else -> "Server Interrupted"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
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