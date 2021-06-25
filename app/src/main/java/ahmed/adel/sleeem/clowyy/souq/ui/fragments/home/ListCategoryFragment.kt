package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentListCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter.ListCategoryAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.HomeViewModel
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
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

class ListCategoryFragment : Fragment() {

    private lateinit var categoryListRecyclerAdapter: ListCategoryAdapter
    private lateinit var binding: FragmentListCategoryBinding
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        categoryListRecyclerAdapter = ListCategoryAdapter(requireContext())
        binding.categoryListRv.adapter = categoryListRecyclerAdapter

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java);
        viewModel.getAllCategories()

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


    }

    override fun onResume() {
        super.onResume()
        subscribeToLiveData()

    }


    private fun subscribeToLiveData() {

        viewModel.categoryLiveData.observe(requireActivity(), Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Log.e("sssss","Loading........")
                    binding.itemProgress.showShimmerAdapter()
                    binding.categoryListRv.visibility = View.GONE
                }
                Resource.Status.ERROR ->{
                    val errorMessage = when (it.message?.toInt()) {
                        400 -> "No Internet Connection"
                        else -> "Server Interrupted"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    binding.itemProgress.hideShimmerAdapter()
                }
                Resource.Status.SUCCESS->{
                    it.data.let {
                        binding.itemProgress.hideShimmerAdapter()
                        binding.categoryListRv.visibility = View.VISIBLE
                        categoryListRecyclerAdapter.changeData(it!!)

                    }
                }
            }
        })
    }



}