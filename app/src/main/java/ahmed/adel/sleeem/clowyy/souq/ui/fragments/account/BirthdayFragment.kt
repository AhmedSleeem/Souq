package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentBirthdayBinding
import ahmed.adel.sleeem.clowyy.souq.utils.Constants
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import java.text.DateFormat
import java.util.*


class BirthdayFragment : Fragment() , DatePicker.OnDateChangedListener {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentBirthdayBinding

    private lateinit var calendar:Calendar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBirthdayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }



        calendar = Calendar.getInstance()

        // Get the Calendar current year, month and day of month
        val thisYear = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Initialize the date picker widget with system current date
        binding.datePicker.init(
            thisYear,
            month,
            day,
            DatePicker.OnDateChangedListener {view, year, monthOfYear, dayOfMonth ->
                // Do something when the date changed in date picker object

                // Display the date picker selected date on text view
                binding.date.setText( formatDate(year,monthOfYear,dayOfMonth))
            })

        binding.datePicker.setOnClickListener{
            // Update the date picker data by a random date
            val year = randomInRange(2000,2025)
            val month = randomInRange(0,11)
            val day = randomInRange(0,27)

            // Update the date picker with random date
            binding.datePicker.updateDate(
                year, // Year
                month, // The month which is starting from zero.
                day // Day of month
            )

            // Toast the new date
            Toast.makeText(
                context,
                "Set Date : ${formatDate(year,month,day)}",
                Toast.LENGTH_SHORT).show()

        }

        binding.saveBtn.setOnClickListener {
            // Get the date picker widget selected date
            val selectedDate = formatDate(binding.datePicker.year,binding.datePicker.month,binding.datePicker.dayOfMonth)
            // Display the date picker selected formatted date
            binding.date.setText( "Selected Date : $selectedDate" )
            updateBirthDay(selectedDate)
            Toast.makeText(requireContext(),"BirthDay Updated Successfully",Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
        }
    }
    fun updateBirthDay(birthDay: String) {
        val sharedPreferences = requireContext().getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("birthDay", birthDay)
        editor.apply()
    }

    private fun formatDate(year:Int, month:Int, day:Int):String{
        // Create a Date variable/object with user chosen date
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time

        // Format the date picker selected date
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }


    // Custom method to get a random number from the provided range
    private fun randomInRange(min:Int, max:Int):Int{
        // Define a new Random class
        val r = Random()

        // Get the next random number within range
        // Including both minimum and maximum number
        return r.nextInt((max - min) + 1) + min;
    }



    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }


}