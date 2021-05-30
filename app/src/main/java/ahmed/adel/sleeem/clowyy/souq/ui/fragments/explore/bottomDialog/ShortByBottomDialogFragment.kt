package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog

import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetShortByBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ShortByBottomDialogFragment: BottomSheetDialogFragment() , View.OnClickListener {

    private lateinit var bindig:BottomSheetShortByBinding

    companion object{
        val TAG = "ShortByBottomDialog"
        fun newInstance(): ShortByBottomDialogFragment {
            return ShortByBottomDialogFragment()
        }
    }

    var mListener: ItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindig = BottomSheetShortByBinding.inflate(layoutInflater,container,false)
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindig.bestMatchTv.setOnClickListener(this)
        bindig.priceHighToLowTv.setOnClickListener(this)
        bindig.priceLowToHighTv.setOnClickListener(this)
        bindig.topRated.setOnClickListener(this)
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
        mListener!!.onItemClick(tvSelected.tag.toString().toInt())
        dismiss()
    }

    interface ItemClickListener {
        fun onItemClick(item: Int)
    }

}