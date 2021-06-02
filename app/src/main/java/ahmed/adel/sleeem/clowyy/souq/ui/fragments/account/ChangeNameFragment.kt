package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentChangeNameBinding
import ahmed.adel.sleeem.clowyy.souq.utils.Constants
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class ChangeNameFragment : Fragment() {
    private lateinit var binding: FragmentChangeNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangeNameBinding.inflate(inflater, container, false)
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
        binding.saveBtn.setOnClickListener {
            val fname = binding.fristName.text.toString().trim()
            val lname = binding.lastName.text.toString().trim()
            if (fname.isNullOrEmpty())
                binding.fristName.error = "required field"
            else if (lname.isNullOrEmpty())
                binding.lastName.error = "required field"
            else{
              updateName("$fname $lname")
                Toast.makeText(
                    requireContext(),
                    "Name updated Successfully",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigateUp()
            }
        }
    }
    fun updateName(name: String) {
        val sharedPreferences = requireContext().getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()
    }
}