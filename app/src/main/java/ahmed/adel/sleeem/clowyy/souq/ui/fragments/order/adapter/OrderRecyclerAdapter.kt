package ahmed.adel.sleeem.clowyy.souq.ui.fragments.order.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemOrderRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.OrderItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class OrderRecyclerAdapter(val data:List<OrderItem>): RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>() {

    var onCellClickListIterator:CellClickListener?=null


   inner class ViewHolder(val binding:ItemOrderRvBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(item:OrderItem) = with(itemView){
           binding.orderId.text = item.id
           binding.orderDate.text = item.date
           binding.orderStatus.text = item.orderStatus
           binding.itemsCount.text = item.itemsCount.toString()
           binding.orderPrice.text = item.price.toString()

           setOnClickListener {
               Navigation.findNavController(it).navigate(R.id.action_orderFragment_to_orderDetailsFragment)
           }
       }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)=holder.bind(data[position])

    interface CellClickListener{
        fun onClick(orderItem:OrderItem, position:Int, view: View);
    }


}