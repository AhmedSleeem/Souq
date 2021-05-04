package ahmed.adel.sleeem.clowyy.souq.ui.offer_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemExploreCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OfferAdapter(private var items: MutableList<SaleItem>) :
    RecyclerView.Adapter<OfferAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSaleRvBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfferAdapter.ViewHolder {
        return ViewHolder(
            ItemSaleRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OfferAdapter.ViewHolder, position: Int) {
        val data = items[position]
        holder.binding.imgProductSaleIv.setImageResource(data.image)
        holder.binding.productNameSaleTc.text = data.name
        holder.binding.costSaleTv.text = data.newPrice.toString()
        holder.binding.oldCostSaleTv.text = data.price.toString()
        holder.binding.offPercentageSaleTv.text = data.salePercent.toString()
    }
}