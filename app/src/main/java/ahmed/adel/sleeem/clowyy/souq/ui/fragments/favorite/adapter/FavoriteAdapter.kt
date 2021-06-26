package ahmed.adel.sleeem.clowyy.souq.ui.fragments.favorite.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemFavoriteGvBinding
import ahmed.adel.sleeem.clowyy.souq.room.FavouriteItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class FavoriteAdapter(val context: Context) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private var items: MutableList<FavouriteItem> = ArrayList()

    fun changeData(newData: MutableList<FavouriteItem>) {
        val oldData = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            FavoriteAdapter.ItemsDiffCallback(
                oldData,
                newData
            )
        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    var itemClickListener: FavoriteAdapter.ItemClickListener? = null

    inner class ViewHolder(val binding: ItemFavoriteGvBinding) :
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

            binding.imgDelete.setOnClickListener {
                itemClickListener!!.onClickdelete(it, item)
            }

            if (itemClickListener != null) {
                setOnClickListener {
                    itemClickListener!!.onClickItem(it, item)
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFavoriteGvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    class ItemsDiffCallback(
        private val oldData: MutableList<FavouriteItem>,
        private val newData: MutableList<FavouriteItem>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }

    interface ItemClickListener {
        fun onClickdelete(view: View, item: FavouriteItem)
        fun onClickItem(view: View, item: FavouriteItem)
    }
}

