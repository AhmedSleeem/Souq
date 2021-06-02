package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemOrderCartItemRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.itemResponse
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OrderProductsRecyclerAdapter(val listener: (View, itemResponse, Int) -> Unit?, val conext : Context) :
    RecyclerView.Adapter<OrderProductsRecyclerAdapter.ViewHolder>() {

    var data = mutableListOf<itemResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderCartItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun changeData(newData: MutableList<itemResponse>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemOrderCartItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: itemResponse) = with(itemView) {

            Glide.with(conext).load(item[0].image).into(binding.itemCartImageView)

            binding.descCartTextView.text = item[0].title
            binding.priceCartTextView.text = item[0].price.toString()

            binding.favoriteCartImageView.setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
            // binding.favoriteCartImageView.setImageResource(R.drawable.ic_love)

            // binding.favoriteCartImageView.setImageResource(R.drawable.ic_love_gray)
        }
    }

}