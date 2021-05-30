package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog

import ahmed.adel.sleeem.clowyy.souq.R
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
        var position = -1
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

        changeRawStyle()
    }



    override fun onClick(v: View?) {
        val tvSelected = v as TextView
        position = tvSelected.tag.toString().toInt()
        changeRawStyle()
        mListener!!.onItemClick(tvSelected.tag.toString().toInt())
        dismiss()
    }

    interface ItemClickListener {
        fun onItemClick(item: Int)
    }

   private fun changeRawStyle(){
        when(position){
            0 -> {
                bindig.bestMatchTv.setBackgroundColor(resources.getColor(R.color.babyBlue))
                bindig.priceHighToLowTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.priceLowToHighTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.topRated.setBackgroundColor(resources.getColor(R.color.white))

            }
            1 -> {
                bindig.bestMatchTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.priceHighToLowTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.priceLowToHighTv.setBackgroundColor(resources.getColor(R.color.babyBlue))
                bindig.topRated.setBackgroundColor(resources.getColor(R.color.white))

            }
            2 -> {
                bindig.bestMatchTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.priceHighToLowTv.setBackgroundColor(resources.getColor(R.color.babyBlue))
                bindig.priceLowToHighTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.topRated.setBackgroundColor(resources.getColor(R.color.white))

            }
            3 -> {
                bindig.bestMatchTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.priceHighToLowTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.priceLowToHighTv.setBackgroundColor(resources.getColor(R.color.white))
                bindig.topRated.setBackgroundColor(resources.getColor(R.color.babyBlue))

            }
        }
    }
}