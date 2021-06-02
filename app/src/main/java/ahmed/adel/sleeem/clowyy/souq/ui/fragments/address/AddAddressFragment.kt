package ahmed.adel.sleeem.clowyy.souq.ui.fragments.address

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentAddAddressBinding
import ahmed.adel.sleeem.clowyy.souq.utils.Constants
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar

class AddAddressFragment : Fragment() {
    private lateinit var binding: FragmentAddAddressBinding
    val args: AddAddressFragmentArgs by navArgs()
    var address: String = ""
    lateinit var type: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        val view = binding.root
        //type of operation edit or add
        type = args.actionType

        val countries = resources.getStringArray(R.array.country_list)
        var countriesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.gender_dropdown_item, countries
        )
        binding.autoCompleteTextView.setAdapter(countriesAdapter)

        val cites = resources.getStringArray(R.array.city_list)
        var citesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.gender_dropdown_item, cites
        )
        binding.autoCompleteCityTextView.setAdapter(citesAdapter)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        if (type == "edit") {
            filterAddress(LoginUtils.getInstance(requireContext())!!.userInfo().Address)
        }

        // add address navigation
        binding.addAddressBtn.setOnClickListener {
            if (checkValidity()) {
                val country = binding.autoCompleteTextView.text.toString().trim()
                val street = binding.streerAddress.text.toString().trim()
                val city = binding.autoCompleteCityTextView.text.toString().trim()
                val state = binding.state.text.toString().trim()
                val zipCode = binding.zipCode.text.toString().trim()
                address = country + "," + city + "," + street + "," + state + "," + zipCode
                Log.e("TAG", "onViewCreated: " + address)
                updateAddress(address)
                Navigation.findNavController(it).navigateUp()

            } else {
                Snackbar.make(
                    binding.root,
                    "ALL Fields end with * Reqiured please fill its .",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun checkValidity(): Boolean {
        if (binding.autoCompleteTextView.text.toString().trim().isNullOrEmpty()) {
            binding.autoCompleteTextView.error = "Field Required!"
            return false
        } else if (binding.autoCompleteCityTextView.text.toString().trim().isNullOrEmpty()) {
            binding.autoCompleteCityTextView.error = "Field Required!"
            return false
        } else if (binding.streerAddress.text.toString().trim().isNullOrEmpty()) {
            binding.streerAddress.error = "Field Required!"
            return false
        } else if (binding.state.text.toString().trim().isNullOrEmpty()) {
            binding.state.error = "Field Required!"
            return false
        }
        return true
    }
    fun updateAddress(address: String) {
        val sharedPreferences = requireContext().getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Address", address)
        editor.apply()
    }
    private fun filterAddress(address: String?) {
        if (address == "N/F" || address.isNullOrEmpty()) {
            Snackbar.make(binding.root, "You Didn`t have address yet", Snackbar.LENGTH_LONG).show()
        } else {
            val list = address.split(",").toTypedArray()
            binding.autoCompleteTextView.hint = list[0]
            binding.autoCompleteCityTextView.hint = list[1]
            binding.streerAddress.hint = list[2]
            binding.state.hint = list[3]
            binding.zipCode.hint = list[4]
        }
    }
}