package ahmed.adel.sleeem.clowyy.souq.ui.fragments.success

import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSuccessBinding
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.offer.OfferFragmentDirections
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


class SuccessFragment : Fragment() ,View.OnClickListener{
    private var _binding: FragmentSuccessBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextShipToButton.setOnClickListener {
            val action = SuccessFragmentDirections.actionSuccessFragmentToOrderFragment()
            it.findNavController().navigate(action)
        }
    }
    override fun onClick(v: View?) {

    }

}

/*


 */