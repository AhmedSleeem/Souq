package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecommendRecyclerAdapter (private var items:MutableList<SaleItem>) : RecyclerView.Adapter<RecommendRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSaleRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSaleRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.binding.imgProductSaleIv.setImageResource(data.image)
        holder.binding.productNameSaleTc.text = data.name
        holder.binding.costSaleTv.text = data.newPrice.toString()
        holder.binding.oldCostSaleTv.text = data.price.toString()
        holder.binding.offPercentageSaleTv.text = data.salePercent.toString()
    }
}