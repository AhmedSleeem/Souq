package ahmed.adel.sleeem.clowyy.souq.ui.fragments.favorite

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentFavoriteBinding
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteItem
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteViewModel
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.favorite.adapter.FavoriteAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class FavoriteFragment : Fragment(), View.OnClickListener {

    private var favoriteAdapter: FavoriteAdapter? = null
    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var binding: FragmentFavoriteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdapter { it, item, _ ->
//            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(item)
//            it.findNavController().navigate(action)
        }


        //userViewModel
        favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)
        favouriteViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            favoriteAdapter!!.getData(it as MutableList<FavouriteItem>)
            binding.favoriteGridView.adapter = favoriteAdapter
            Log.i("mnem", it.size.toString())
        })

//        binding.floatingActionBtn.setOnClickListener(this)
//        binding.btnDeleteAll.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
//        when (v) {
//            binding.floatingActionBtn -> {
//                findNavController().navigate(R.id.action_listFragment_to_addFragment)
//            }
//            binding.btnDeleteAll -> {
//                deleteAllUser()
//            }
//        }
    }

}