package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog

import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetFilterCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.SearchResultViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog.adapter.FilterCategoryAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterByCategoryBottomDialogFragment: BottomSheetDialogFragment() {

    private lateinit var bindig:BottomSheetFilterCategoryBinding
    private lateinit var viewModel:SearchResultViewModel
    private lateinit var adapter: FilterCategoryAdapter


    companion object{
        var  position=-1;
        val TAG = "CategoryBottomDialog"
        fun newInstance(): FilterByCategoryBottomDialogFragment {
            return FilterByCategoryBottomDialogFragment()
        }
    }

    var mListener: ItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindig = BottomSheetFilterCategoryBinding.inflate(layoutInflater,container,false)
        adapter = FilterCategoryAdapter()
        viewModel = ViewModelProvider(requireActivity()).get(SearchResultViewModel::class.java)
        subscribeToLiveData()
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindig.filterCategoriesRv.adapter = adapter

        adapter.onItemClickListener = object : FilterCategoryAdapter.OnItemClickListener{
            override fun onClick(category: String ) {
                if (position == -1){
                    mListener!!.onItemClick(null)
                }else {
                    mListener!!.onItemClick(category)
                }
                this@FilterByCategoryBottomDialogFragment.dismiss()
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.categoriesLiveDirections.observe(viewLifecycleOwner, Observer {
            adapter.changeData(it)
        })
    }



    interface ItemClickListener {
        fun onItemClick(category: String?)
    }

}