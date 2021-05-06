package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemOrderRvBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.Order
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OrderRecyclerAdapter(val data:List<Order>): RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>() {
   inner class ViewHolder(val binding:ItemOrderRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.orderId.text = item.id
        holder.binding.orderDate.text = item.date
        holder.binding.orderStatus.text = item.orderStatus
        holder.binding.itemsCount.text = item.itemsCount.toString()
        holder.binding.orderPrice.text = item.price.toString()

    }
}