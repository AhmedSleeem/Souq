package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog

import ahmed.adel.sleeem.clowyy.souq.databinding.BottomSheetFilterPriceBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.NumberFormat
import java.util.*


class FilterByPriceBottomDialogFragment: BottomSheetDialogFragment() , View.OnClickListener {

    private lateinit var bindig:BottomSheetFilterPriceBinding

    companion object{
        val TAG = "PriceBottomDialog"
        fun newInstance(): FilterByPriceBottomDialogFragment {
            return FilterByPriceBottomDialogFragment()
        }
    }

    var mListener: ItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindig = BottomSheetFilterPriceBinding.inflate(layoutInflater,container,false)
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindig.doneBtn.setOnClickListener(this)

        bindig.rangeSlider.addOnChangeListener { rangeSlider, value, fromUser ->
            bindig.etMin.text = rangeSlider.values[0].toString()
            bindig.etMax.text = rangeSlider.values[1].toString()
        }

        bindig.rangeSlider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 1
            format.currency = Currency.getInstance("EGP")
            format.format(value.toDouble())
        }
    }

    override fun onClick(v: View?) {
        val min = bindig.etMin.text.toString().toFloat().toInt()
        val max = bindig.etMax.text.toString().toFloat().toInt()
        mListener!!.onItemClick(min,max)
        dismiss()
    }

    interface ItemClickListener {
        fun onItemClick(min: Int , max: Int)
    }

}