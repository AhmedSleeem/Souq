package ahmed.adel.sleeem.clowyy.souq.ui.favorite_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemFavoriteGvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.database.DataSetObserver
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(private var items: MutableList<SaleItem>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemFavoriteGvBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.ViewHolder {
        return ViewHolder(
            ItemFavoriteGvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val data = items[position]

        holder.binding.imgProduct.setImageResource(data.image)
        holder.binding.tvProductName.text = data.name
        holder.binding.tvCost.text = data.newPrice.toString()
        holder.binding.tvOldCost.text = data.price.toString()
        holder.binding.tvOffPercentage.text = data.salePercent.toString()
    }

}