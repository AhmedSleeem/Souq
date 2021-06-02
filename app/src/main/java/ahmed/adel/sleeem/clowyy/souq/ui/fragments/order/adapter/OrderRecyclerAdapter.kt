package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemOrderRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.OrderResponse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OrderRecyclerAdapter(val listener: (View, OrderResponse.OrderResponseItem, Int) -> Unit?) :
    RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>() {

    private var data = listOf<OrderResponse.OrderResponseItem>()
    fun changeData(newData: List<OrderResponse.OrderResponseItem>) {

        data = newData
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemOrderRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderResponse.OrderResponseItem) = with(itemView) {
            binding.orderId.text = item.orderCode
            binding.orderDate.text = "Order at Sell3a : "+item.orderDate
            binding.orderStatus.text = item.orderState
            binding.orderPrice.text = item.totalPrice.toString()
            var itemsCount = 0.0
            for (itm in item.itemIds) {
                itemsCount += itm.count
            }

            binding.itemsCount.text = itemsCount.toString()
            // binding.orderPrice.text = item.price.toString()

            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }


    }

//    fun changeData(data: MutableList<OrderResponse.OrderResponseItem>) {
//        this.data = data
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])


}