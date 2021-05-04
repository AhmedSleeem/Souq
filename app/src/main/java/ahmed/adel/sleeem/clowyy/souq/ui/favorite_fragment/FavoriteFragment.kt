package ahmed.adel.sleeem.clowyy.souq.ui.favorite_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentFavoriteBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import ahmed.adel.sleeem.clowyy.souq.ui.favorite_fragment.adapter.FavoriteAdapter
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteFragment : Fragment() {

    private var favoriteGridView: RecyclerView? = null
    private var favoriteAdapter: FavoriteAdapter? = null
    private lateinit var binding: FragmentFavoriteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        favoriteGridView = view.findViewById(R.id.favorite_gridView)
        favoriteAdapter = FavoriteAdapter(items = favList)

        binding.favoriteGridView.adapter = favoriteAdapter
        favoriteGridView?.adapter = favoriteAdapter

    }

    var favList = mutableListOf<SaleItem>(

        SaleItem(R.drawable.bag2, "FS - Nike Air Max 270 React...", "24% Off", 299.34f, 534.34f),
        SaleItem(R.drawable.shoes, "FS - Nike Air Max 270 React...", "24% Off", 299.34f, 534.34f),
        SaleItem(R.drawable.shoes2, "FS - Nike Air Max 270 React...", "24% Off", 299.34f, 534.34f),
        SaleItem(
            R.drawable.womem_bag,
            "FS - Nike Air Max 270 React...",
            "24% Off",
            299.34f,
            534.34f
        ),
        SaleItem(R.drawable.shoes, "FS - Nike Air Max 270 React...", "24% Off", 299.34f, 534.34f),
        SaleItem(R.drawable.bag2, "FS - Nike Air Max 270 React...", "24% Off", 299.34f, 534.34f),
        SaleItem(R.drawable.shoes2, "FS - Nike Air Max 270 React...", "24% Off", 299.34f, 534.34f)
    )


}