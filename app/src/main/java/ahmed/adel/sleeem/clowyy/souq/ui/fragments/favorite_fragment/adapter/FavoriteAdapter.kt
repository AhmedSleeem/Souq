package ahmed.adel.sleeem.clowyy.souq.ui.fragments.favorite_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemFavoriteGvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(private var items: MutableList<SaleItem>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemFavoriteGvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SaleItem) = with(itemView) {
            binding.imgProduct.setImageResource(item.image)
            binding.tvProductName.text = item.name
            binding.tvCost.text = item.newPrice.toString()
            binding.tvOldCost.text = item.price.toString()
            binding.tvOffPercentage.text = item.salePercent.toString()

            setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_favoriteFragment_to_detailsFragment)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.ViewHolder {
        return ViewHolder(
            ItemFavoriteGvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) = holder.bind(items[position])

}