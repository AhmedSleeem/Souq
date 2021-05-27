package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog

import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetFilterSaleBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetShortByBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterBySaleBottomDialogFragment: BottomSheetDialogFragment() , View.OnClickListener {

    private lateinit var bindig:BottomSheetFilterSaleBinding

    companion object{
        val TAG = "SaleBottomDialog"
        fun newInstance(): FilterBySaleBottomDialogFragment {
            return FilterBySaleBottomDialogFragment()
        }
    }

    var mListener: ItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindig = BottomSheetFilterSaleBinding.inflate(layoutInflater,container,false)
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindig.saleItemsTv.setOnClickListener(this)
        bindig.allProductsTv.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        var sale = false
        when(v){
            bindig.saleItemsTv->sale=true
            bindig.allProductsTv->sale=false
        }
        mListener!!.onItemClick(sale)
        dismiss()
    }

    interface ItemClickListener {
        fun onItemClick(isBySale : Boolean)
    }

}