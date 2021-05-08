package ahmed.adel.sleeem.clowyy.souq.ui.fragments.account

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentProfileBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
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

        // userName navigation
        binding.usernameTextView.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_changeNameFragment);

        }
        // choose gender navigation
        binding.genderLayout.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_chooseGenderFragment);

        }

        // change birthday navigation
        binding.birthdayLayout.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_birthdayFragment);

        }

        // change mail navigation
        binding.mailLayout.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_changeMailFragment);

        }

        // change mail navigation
        binding.phoneLayout.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_phoneNumberFragment);

        }

        // change password navigation
        binding.passwordLayout.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_changePasswordFragment);

        }
    }


}