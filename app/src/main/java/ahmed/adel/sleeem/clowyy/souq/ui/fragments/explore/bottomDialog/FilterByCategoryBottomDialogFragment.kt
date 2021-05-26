package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog

import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetFilterCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetShortByBinding
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter.FilterCategoryAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.SearchResultViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterByCategoryBottomDialogFragment: BottomSheetDialogFragment() , View.OnClickListener {

    private lateinit var bindig:BottomSheetFilterCategoryBinding
    private lateinit var viewModel:SearchResultViewModel
    private lateinit var adapter:FilterCategoryAdapter

    companion object{
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
            override fun onClick(category: String) {

            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.getCategoriesAndCount().observe(viewLifecycleOwner, Observer {
            adapter.changeData(it)
        })
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is ItemClickListener)
//            mListener = context
//        else
//            throw RuntimeException(context.toString()
//                    + " must implement ItemClickListener");
//    }

//    override fun onDetach() {
//        super.onDetach()
//        mListener = null
//    }

    override fun onClick(v: View?) {
        val tvSelected = v as TextView
        mListener!!.onItemClick(tvSelected.tag.toString())
        dismiss()
    }

    interface ItemClickListener {
        fun onItemClick(item: String)
    }

}