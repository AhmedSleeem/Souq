package ahmed.adel.sleeem.clowyy.souq.ui.fragments.offerType

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOfferTypeBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.offer.OfferViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.offerType.adapter.OfferTypeAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController


class OfferTypeFragment : Fragment() {

    private var saleData = arrayListOf<ProductResponse.Item>()
    private lateinit var saleRecyclerAdapter: OfferTypeAdapter
    private lateinit var binding: FragmentOfferTypeBinding
    private lateinit var viewmodel: OfferViewModel
    private lateinit var saleTitle: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfferTypeBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.let {
            val args = OfferTypeFragmentArgs.fromBundle(it)
            saleTitle = args.saleTitle
        }

        saleRecyclerAdapter = OfferTypeAdapter(requireContext())
        binding.recommendedRv.adapter = saleRecyclerAdapter

        return view
    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init viewmodel
        viewmodel = ViewModelProvider(requireActivity()).get(OfferViewModel::class.java)
        viewmodel.getItemsBySale(saleTitle)



        binding.saleTitle.setText(saleTitle)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setTitle(saleTitle)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        binding.retryButton.setOnClickListener {
            val action = OfferTypeFragmentDirections.actionOfferTypeFragmentSelf(saleTitle)
            it.findNavController().navigate(action)
        }

    }


    private fun subscribeToLiveData() {
        viewmodel.filterLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.offerProgress.visibility = View.VISIBLE
                    binding.retryView.visibility = View.INVISIBLE
                    Log.e("sss", "loading........")
                }
                Resource.Status.ERROR -> {
                    val errorMessage = when (it.message?.toInt()) {
                        400 -> "No Internet Connection"
                        else -> "Server Interrupted"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    binding.retryView.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        binding.retryView.visibility = View.INVISIBLE
                        binding.offerProgress.visibility = View.GONE
                        saleRecyclerAdapter.changeData(it!!)
                        saleData = it
                    }

                }
            }
        })


    }


}