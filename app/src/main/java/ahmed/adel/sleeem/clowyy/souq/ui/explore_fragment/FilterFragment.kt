package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentFilterBinding
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.slider.RangeSlider
import java.text.NumberFormat
import java.util.*


class FilterFragment : Fragment() {

    private lateinit var rangeSlider: RangeSlider
    private lateinit var etMin: TextView
    private lateinit var etMax: TextView
    private lateinit var binding: FragmentFilterBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rangeSlider = view.findViewById(R.id.rangeSlider)
        etMin = view.findViewById(R.id.et_min)
        etMax = view.findViewById(R.id.et_max)


        //rangeSlider
        rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being started
//
//                slider.setValues(min)
//                slider.setValues(max)

//                slider.setValues(etMin.text.toString().toFloat(), etMax.text.toString().toFloat())


            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being stopped
//                rangeSlider.setValues(min,max)

//                slider.setValues(etMin.text.toString().toFloat(), etMax.text.toString().toFloat())


            }
        })

        rangeSlider.addOnChangeListener { rangeSlider, value, fromUser ->
            // Responds to when slider's value is changed


//             rangeSlider.setValues(min,max)
//        min = etMin.text.toString().toFloat()
//        max = etMax.text.toString().toFloat()


//            rangeSlider.setValues(etMin.text.toString().toFloat(), etMax.text.toString().toFloat())
//            rangeSlider.setValues(etMin.text.toString().toFloat(),etMax.text.toString().toFloat())

            etMin.text = rangeSlider.values[0].toString()
            etMax.text = rangeSlider.values[1].toString()


        }

        rangeSlider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 3
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }

    }

}

