package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemCategoryListRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListCategoryAdapter (private var items: MutableList<ExplorerItem>) :
    RecyclerView.Adapter<ListCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryListRvBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemCategoryListRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.binding.imgProduct.setImageResource(data.categoryImage)
        holder.binding.tvProduct.text = data.categoryName

    }
}