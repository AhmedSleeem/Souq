package ahmed.adel.sleeem.clowyy.souq.ui.fragments.favorite

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentFavoriteBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteItem
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteViewModelRoom
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.favorite.adapter.FavoriteAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.OrderDetailsViewModel
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class FavoriteFragment : Fragment() {

    private var favoriteAdapter: FavoriteAdapter? = null
    private lateinit var favouriteViewModelRoom: FavouriteViewModelRoom
    private lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: FavouriteViewModel
    private lateinit var itemFav: FavouriteItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root


        favoriteAdapter = FavoriteAdapter(requireContext());
        binding.favoriteGridView.adapter = favoriteAdapter

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //userViewModel
        favouriteViewModelRoom = ViewModelProvider(this).get(FavouriteViewModelRoom::class.java)
        favouriteViewModelRoom.readAllData.observe(viewLifecycleOwner, Observer {
            favoriteAdapter!!.changeData(it as MutableList<FavouriteItem>)
            binding.favoriteGridView.adapter = favoriteAdapter
            Log.i("mnem", it.size.toString())
        })

        favoriteAdapter!!.itemClickListener = object : FavoriteAdapter.ItemClickListener {
            override fun onClickdelete(view: View, item: FavouriteItem) {
                deleteItem(item)
            }

            override fun onClickItem(view: View, item: FavouriteItem) {
                itemFav = item
                initViewModel()
                getItemById()
                Log.i("a", item.productName)
                Toast.makeText(requireContext(), "item clicked", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)
        viewModel.getItemsById(itemFav.itemId)
    }

    private fun deleteItem(item: FavouriteItem) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES") { _, _ ->
            favouriteViewModelRoom.deleteItem(item)
            Toast.makeText(requireContext(), "item deleted", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("NO") { _, _ -> }
        builder.setTitle("Delete !")
        builder.setMessage("Are you sure yo want to delete ${item.productName} ?")
        builder.create().show()
    }

    private fun getItemById() {
        viewModel.item.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("TAG", "getAllOrders: LOADING")
                }
                Resource.Status.ERROR -> {
                    Log.e("TAG", "getAllOrders: ERROR" + it.message)

                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        Log.e("TAG", "getAllOrders: ERROR" + it?.size)
//                        val mnem : ProductResponse.Item = ProductResponse.Item(it[0].brand,it[0].category,it[0].color,it[0].companyName,it[0].description,it[0].id,it[0].image,it[0].price)
//
//                        val action =
//                            FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(mnem)
//                        findNavController().navigate(action)
                    }
                }
            }
        })
    }

}