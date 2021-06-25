package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentProfileBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.FullUserInfo
import ahmed.adel.sleeem.clowyy.souq.pojo.request.UserRequist
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var fullUserInfo: FullUserInfo
    private lateinit var userRequist: UserRequist
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        addUserInfo()
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        userRequist = LoginUtils.getInstance(requireContext())!!.getUserRequist()
        viewModel.updateUserInfo(userRequist)
        updateUser()

        // userName navigation
        binding.usernameTextView.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_profileFragment_to_changeNameFragment);

        }
        // choose gender navigation
        binding.genderLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_profileFragment_to_chooseGenderFragment);

        }

        // change birthday navigation
        binding.birthdayLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_profileFragment_to_birthdayFragment);

        }

        // change mail navigation
        binding.addressLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_profileFragment_to_adressFragment);

        }

        // change mail navigation
        binding.phoneLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_profileFragment_to_phoneNumberFragment);

        }

        // change password navigation
        binding.passwordLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_profileFragment_to_changePasswordFragment);

        }

    }

    private fun addUserInfo() {
        fullUserInfo = LoginUtils.getInstance(requireContext())!!.userInfo()
        binding.moreGenderTxt.text = fullUserInfo.Gender
        binding.usernameTextView.text = fullUserInfo.name
        binding.mailTextview.text = fullUserInfo.email

        Glide
            .with(requireContext())
            .load(fullUserInfo.profileImage)
            .centerCrop()
            .error(R.drawable.profile)
            .placeholder(R.drawable.profile)
            .into(binding.profileImage)
        binding.moreGenderTxt.text = fullUserInfo.Gender
        binding.moreBirthdayTxt.text = fullUserInfo.BirthDate
        binding.morePhoneTxt.text = fullUserInfo.phoneNumber
        filterAddress(fullUserInfo.Address)
    }


    private fun updateUser() {
        viewModel.userInfo.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    val errorMessage =    when(it.message?.toInt()){
                        400  -> "No Internet Connection"
                        else -> "Server Interrupted"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()

                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        Log.e("sssss", it?.email!!)
                        // LoginUtils.getInstance(requireContext())?.saveUserInfo(it)
                    }
                }
            }
        })
    }
    private fun filterAddress(address: String?) {
        if (address.isNullOrEmpty()|| address == "N/F") {
            binding.moreMailTxt.text = "N/F"
        } else {
            val list = address.split(",").toTypedArray()
            binding.moreMailTxt.text = list[0]+" - "+list[1]
        }
    }
}