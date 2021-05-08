package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecommendedRecyclerAdapter(private var items:MutableList<SaleItem>) : RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRecommendedRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemRecommendedRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.binding.imgProduct.setImageResource(data.image)
        holder.binding.tvProductName.text = data.name
        holder.binding.tvCost.text = data.newPrice.toString()
        holder.binding.tvOldCost.text = data.price.toString()
        holder.binding.tvOffPercentage.text = data.salePercent.toString()
        holder.binding.ratingBar.rating = 4.0f
    }
}