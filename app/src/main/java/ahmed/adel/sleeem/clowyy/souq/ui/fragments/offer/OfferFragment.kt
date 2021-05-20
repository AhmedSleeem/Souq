package ahmed.adel.sleeem.clowyy.souq.ui.fragments.offer

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentNotificationBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOfferBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentProfileBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController


class OfferFragment : Fragment() {
    private lateinit var binding: FragmentOfferBinding
    var navController : NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfferBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.flashSaleLayout.setOnClickListener{
            val saleTitle = "flash sale"//binding.flashSaleTitle.text.toString()
            val action = OfferFragmentDirections.actionOfferFragmentToOfferTypeFragment(saleTitle)
            it.findNavController().navigate(action)
        }

        binding.MegaSale.setOnClickListener{
            val saleTitle = "mega sale"//binding.MegaSaleTv.text.toString()
            val action = OfferFragmentDirections.actionOfferFragmentToOfferTypeFragment(saleTitle)
            it.findNavController().navigate(action)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }



    }