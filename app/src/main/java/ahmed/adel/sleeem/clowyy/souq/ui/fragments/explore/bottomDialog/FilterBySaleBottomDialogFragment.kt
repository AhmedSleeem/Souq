package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog

import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetFilterSaleBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterBySaleBottomDialogFragment: BottomSheetDialogFragment() , View.OnClickListener {

    private lateinit var bindig:BottomSheetFilterSaleBinding

    companion object{
        val TAG = "SaleBottomDialog"
        var position = -1
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

        if(position == -1)bindig.allProductIV.visibility = View.VISIBLE
        else bindig.saleIV.visibility = View.VISIBLE

    }


    override fun onClick(v: View?) {
        var sale = 0
        when(v){
            bindig.saleItemsTv->{
                if (position==-1) {
                    bindig.saleIV.visibility = View.VISIBLE
                    bindig.allProductIV.visibility = View.GONE
                }
                sale = 1
                position = 1
            }

            bindig.allProductsTv->{
                if (position !=-1) {
                    bindig.saleIV.visibility = View.GONE
                    bindig.allProductIV.visibility = View.VISIBLE
                }
                sale = 0
            }
        }

        mListener!!.onItemClick(sale)
        dismiss()
    }

    interface ItemClickListener {
        fun onItemClick( sale : Int)
    }

}