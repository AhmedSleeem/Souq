package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog

import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetFilterBrandBinding
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog.adapter.FilterBrandAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.SearchResultViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterByBrandBottomDialogFragment: BottomSheetDialogFragment() , View.OnClickListener {

    private lateinit var bindig:BottomSheetFilterBrandBinding
    private lateinit var viewModel:SearchResultViewModel
    private lateinit var adapter: FilterBrandAdapter

    companion object{
        val TAG = "BrandBottomDialog"
        fun newInstance(): FilterByBrandBottomDialogFragment {
            return FilterByBrandBottomDialogFragment()
        }
    }

    var mListener: ItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindig = BottomSheetFilterBrandBinding.inflate(layoutInflater,container,false)
        adapter = FilterBrandAdapter()
        viewModel = ViewModelProvider(requireActivity()).get(SearchResultViewModel::class.java)
        subscribeToLiveData()
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindig.filterBrandTV.adapter = adapter
    }

    private fun subscribeToLiveData() {
        viewModel.getBrands().observe(viewLifecycleOwner, Observer {
            adapter.changeData(it)
        })
    }


    override fun onClick(v: View?) {
        val tvSelected = v as TextView
        mListener!!.onItemClick(tvSelected.tag.toString())
        dismiss()
    }

    interface ItemClickListener {
        fun onItemClick(item: String)
    }

}