package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentCartBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.CartItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.review.ReviewFragmentDirections
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class CartFragment : Fragment(),View.OnClickListener {
    private lateinit var cartAdapter: CartAdapter
    private var _binding: FragmentCartBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // app bar arrow back
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        initCartRecyclerView()
        binding.checkOutButton.setOnClickListener(this)
    }

    private fun initCartRecyclerView() {
        cartAdapter = CartAdapter { _, _, _ -> }

        var item1 = CartItem()

        var item2 = CartItem(itemIsFavorite = false)
        var item3 = CartItem()
        var item4 = CartItem(itemIsFavorite = false)
        var starList = mutableListOf<CartItem>(item1, item2,item3,item4)
        cartAdapter.swapData(starList)
        binding.cartRecyclerView.apply {
            adapter = cartAdapter
        }
    }
    override fun onClick(v: View) {
        when (v) {
            binding.checkOutButton ->{
                val action = CartFragmentDirections.actionCartFragmentToShipToFragment()
                view?.findNavController()?.navigate(action)
            }
        }
    }

}