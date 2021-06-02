package ahmed.adel.sleeem.clowyy.souq.ui.fragments.offer

import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentOfferBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.Cupone
import ahmed.adel.sleeem.clowyy.souq.utils.CuponeUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlin.random.Random


class OfferFragment : Fragment() {
    private lateinit var binding: FragmentOfferBinding
    var navController : NavController? = null
    private lateinit var cuponeObj : Cupone
    private lateinit var cuponeUtils : CuponeUtils


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

        cuponeUtils  = CuponeUtils(requireContext())

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
        cuponeObj = Cupone()
        cuponeObj.cuponeCode = getRandomString(6)
        cuponeObj.cuponeCodeValue = randValue(1 , 7)
        val cupon:Cupone?=cuponeUtils.getCupone()
        if (cupon == null){
            cuponeUtils.editCupone(cuponeObj)
            updateCuponUi()
        }else{
            updateCuponUi()
        }

    }
    fun updateCuponUi(){
       val cupone:Cupone = cuponeUtils.getCupone()!!
        binding.cupone.text = "Use “${cupone!!.cuponeCode}” Cupon For Get “${cupone!!.cuponeCodeValue}” % off"
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun randValue(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }

    }