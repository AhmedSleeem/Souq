package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentExploreBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.CategoryResponse
import ahmed.adel.sleeem.clowyy.souq.receiver.NetworkChangeReceiver
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.adapter.ExploreCategoryAdapter
import ahmed.adel.sleeem.clowyy.souq.utils.InternetUtils.Companion.isNetworkConnected
import ahmed.adel.sleeem.clowyy.souq.utils.OnNetworkListener
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
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
import com.mancj.materialsearchbar.MaterialSearchBar
import java.net.SocketTimeoutException


class ExploreFragment : Fragment(), OnNetworkListener {

    private lateinit var mNetworkReceiver: NetworkChangeReceiver
    lateinit var categoryAdapter: ExploreCategoryAdapter
    private lateinit var binding: FragmentExploreBinding
    lateinit var viewModel: ExploreViewModel;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        categoryAdapter = ExploreCategoryAdapter(requireContext())
        mNetworkReceiver = NetworkChangeReceiver()
        mNetworkReceiver.setOnNetworkListener(this)
        return binding.root
    }

    private var lastSearches = mutableListOf<String>("mahmoud", "hager")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init view model
        viewModel = ViewModelProvider(requireActivity()).get(ExploreViewModel::class.java)

        subscribeToLiveData()

        binding.retryButton.setOnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentSelf()
            it.findNavController().navigate(action)
        }
        viewModel.getCategories()

        //enable binding.searchBar callbacks
        binding.searchBar.setOnSearchActionListener(object :
            MaterialSearchBar.OnSearchActionListener {
            override fun onButtonClicked(buttonCode: Int) {
//                when (buttonCode) {
//                    MaterialSearchBar.B -> //openVoiceRecognizer()
//                }
            }

            override fun onSearchStateChanged(enabled: Boolean) {
//                val s = if (enabled) "enabled" else "disabled"
//                Toast.makeText(requireContext(), "Search $s", Toast.LENGTH_SHORT).show()
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                val action = ExploreFragmentDirections.actionExploreFragmentToSearchSucceedFragment(
                    query = text.toString(),
                    searchStatus = SearchSucceedFragment.SearchStatus.QUERY
                )
                view.findNavController().navigate(action)
            }
        });
        binding.searchBar.lastSuggestions = lastSearches;


        //category click listener
        categoryAdapter.setOnItemClickListener =
            object : ExploreCategoryAdapter.OnItemClickListener {
                override fun onClick(
                    view: View,
                    item: CategoryResponse.CategoryResponseItem
                ) {
                    val action =
                        ExploreFragmentDirections.actionExploreFragmentToSearchSucceedFragment(
                            query = item.name,
                            searchStatus = SearchSucceedFragment.SearchStatus.CATEGORY
                        )
                    view.findNavController().navigate(action)
                }
            }

//        binding.notificationIv.setOnClickListener {
//            Navigation.findNavController(it)
//                .navigate(R.id.action_exploreFragment_to_searchSucceedFragment);
//        }
        binding.favoriteIv.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_exploreFragment_to_favoriteFragment);
        }

        binding.categoryRv.adapter = categoryAdapter
    }

    private fun subscribeToLiveData() {
        if (isNetworkConnected(requireContext())) {
            viewModel.categoriesLiveData.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.retryView.visibility = View.INVISIBLE
                        binding.categoryProgress.hideShimmerAdapter()
                        binding.categoryRv.visibility = View.VISIBLE
                        categoryAdapter.changeData(it.data!!)
                    }
                    Resource.Status.ERROR -> {
                        //  Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.retryView.visibility = View.VISIBLE
                        //binding.textView6.text = it.message.toString()

//                        Toast.makeText(
//                            requireContext(),
//                            "An error occured:",// ${it.message.toString()}
//                            Toast.LENGTH_LONG
//                        ).show()
                        binding.categoryProgress.hideShimmerAdapter()
                    }
                    Resource.Status.LOADING -> {
                        binding.retryView.visibility = View.INVISIBLE
                        binding.categoryProgress.showShimmerAdapter()
                        binding.categoryRv.visibility = View.GONE

                    }
                }
            })
        } else {
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG).show()
            Log.e("TAG", "subscribeToLiveData:1 No Internet Connection")
        }

    }

    private fun registerConnectionReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val receiver = NetworkChangeReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            requireActivity().registerReceiver(receiver, intentFilter)
        }
    }

    override fun onNetworkConnected() {
        //Toast.makeText(requireContext(), "Internet Connected", Toast.LENGTH_LONG).show()
    }

    override fun onNetworkDisconnected() {
        Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG).show()
        Log.e("TAG", "subscribeToLiveData:2 No Internet Connection")
    }

    override fun onStart() {
        super.onStart()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().registerReceiver(
                mNetworkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
            subscribeToLiveData()
        }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(mNetworkReceiver)
    }
}