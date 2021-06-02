package ahmed.adel.sleeem.clowyy.souq.ui.fragments.favorite.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemFavoriteGvBinding
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavoriteAdapter(val listener: (View, FavouriteItem, Int) -> Unit?) :
    RecyclerView.Adapter<FavoriteAdapter.RepoViewHolder>() {

    private var data: MutableList<FavouriteItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemFavoriteGvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(data[position])

    fun getData(data: MutableList<FavouriteItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class RepoViewHolder(var binding: ItemFavoriteGvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavouriteItem) = with(itemView) {

            Glide.with(context)
                .load(item.productImage)
                .fitCenter()
                .into(binding.imgProduct)
            binding.tvProductName.text = item.productName
            binding.tvCost.text = item.price.toString()
            binding.tvOldCost.text = item.price.toString()
            binding.tvOffPercentage.text = item.offer.toString()

            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}