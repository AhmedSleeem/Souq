package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemOrderCartItemRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.OrderProductItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OrderProductsRecyclerAdapter(val data:List<OrderProductItem>): RecyclerView.Adapter<OrderProductsRecyclerAdapter.ViewHolder>() {
   inner class ViewHolder(val binding:ItemOrderCartItemRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderCartItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.itemCartImageView.setImageResource(item.img)
        holder.binding.descCartTextView.text = item.name
        holder.binding.priceCartTextView.text = item.price.toString()

        if (item.isFavorite)
            holder.binding.favoriteCartImageView.setImageResource(R.drawable.ic_love)
        else
            holder.binding.favoriteCartImageView.setImageResource(R.drawable.ic_love_gray)

    }
}