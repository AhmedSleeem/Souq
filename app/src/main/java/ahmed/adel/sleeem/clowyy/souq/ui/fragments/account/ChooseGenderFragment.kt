package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentChooseGenderBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.request.UserRequist
import ahmed.adel.sleeem.clowyy.souq.utils.Constants
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


class ChooseGenderFragment : Fragment() {

    private lateinit var userRequist: UserRequist
    private lateinit var binding: FragmentChooseGenderBinding
    //  private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseGenderBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        // viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)

        val types = resources.getStringArray(R.array.gender_list)
        var arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.gender_dropdown_item, types
        )
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.saveBtn.setOnClickListener {
            val gender = binding.autoCompleteTextView.text.toString()
            when (gender) {
                "Male", "Female" -> {
                    updateGender(gender.toLowerCase())
                    Toast.makeText(
                        requireContext(),
                        "Gender Updated Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigateUp()
//                    userRequist = LoginUtils.getInstance(requireContext())!!.getUserRequist()
//                    viewModel.updateUserInfo(userRequist)
//                    updateUser()
                }
                else -> Toast.makeText(
                    requireContext(),
                    "Please Choose Gender (M/F)",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
    fun updateGender(gender: String) {
        val sharedPreferences = requireContext().getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("gender", gender)
        editor.apply()
    }
    /*  private fun updateUser() {
         viewModel.userInfo.observe(requireActivity(), Observer {
              when (it.status) {
                  Resource.Status.LOADING -> {
                      Log.e("sssss", "Loading........")
                  }
                  Resource.Status.ERROR -> {
                      Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

                  }
                  Resource.Status.SUCCESS -> {
                      it.data.let {
  //                        Log.e("sssss", it?.email!!)
  //                        LoginUtils.getInstance(requireActivity())!!.saveUserInfo(it)
                       //   Toast.makeText(requireContext(),"Gender Updated Successfully",Toast.LENGTH_LONG).show()
                        findNavController().navigateUp()
                      }
                  }
              }
          })
      }*/
}