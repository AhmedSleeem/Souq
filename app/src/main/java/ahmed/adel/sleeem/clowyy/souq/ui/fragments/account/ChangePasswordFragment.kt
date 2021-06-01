package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.api.Resource
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentChangePasswordBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.request.PasswordRequest
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
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
import androidx.navigation.fragment.findNavController


class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var passwordRequest: PasswordRequest
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        viewModel = ViewModelProvider(requireActivity()).get(ChangePasswordViewModel::class.java)
        binding.saveBtn.setOnClickListener {
            val oldPass = binding.oldPassword.text.toString().trim()
            val newpass = binding.newPassword.text.toString().trim()
            val newPassAgain = binding.newPasswordAgain.text.toString().trim()
            if (oldPass.isNullOrEmpty() || newpass.isNullOrEmpty() || newPassAgain.isNullOrEmpty()) {
                binding.oldPassword.error = "required field"
            } else {
                if (!(oldPass.equals(
                        LoginUtils.getInstance(requireContext())!!.userInfo().password
                    ))
                ) {
                    Toast.makeText(requireContext(), "old password is incorrect", Toast.LENGTH_LONG)
                        .show()
                } else if (!(newpass.equals(newPassAgain))) {
                    Toast.makeText(requireContext(), "password are different", Toast.LENGTH_LONG)
                        .show()
                } else {
                    passwordRequest =
                        PasswordRequest(
                            LoginUtils.getInstance(requireContext())!!.userInfo().email,
                            newpass,
                            oldPass
                        )
                    viewModel.updatePassword(passwordRequest)
                    updatePassword(newpass)
                }
            }
        }


    }

    private fun updatePassword(newpass: String) {
        viewModel.password.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        LoginUtils.getInstance(requireContext())!!.updatePassword(newpass)
                        Toast.makeText(
                            requireContext(),
                            "Password updated Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigateUp()
                    }
                }
            }
        })
    }

}