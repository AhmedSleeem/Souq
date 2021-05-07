package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemCartBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.CartItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class CartAdapter(val listener: (View, CartItem, Int) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var data: MutableList<CartItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: MutableList<CartItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getRepoAt(position: Int): CartItem? {
        return data.get(position)
    }

    inner class CartViewHolder(var binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) = with(itemView) {
            binding.descCartTextView.text = item.itemDesc
            binding.priceCartTextView.text = item.itemPrice
            binding.countCartTextView.text = item.itemCount.toString()
            binding.itemCartImageView.setImageResource(item.itemImage)
            if(item.itemIsFavorite)
                binding.favoriteCartImageView.setImageResource(R.drawable.ic_love)
            else
                binding.favoriteCartImageView.setImageResource(R.drawable.ic_empty_love)

            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}