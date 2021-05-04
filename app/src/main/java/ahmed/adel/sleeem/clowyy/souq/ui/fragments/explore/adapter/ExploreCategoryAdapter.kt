package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemExploreCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ExploreCategoryAdapter(private var items: MutableList<ExplorerItem>) :
    RecyclerView.Adapter<ExploreCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemExploreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExploreCategoryAdapter.ViewHolder {
        return ViewHolder(
            ItemExploreCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExploreCategoryAdapter.ViewHolder, position: Int) {
        val data = items[position]
        holder.binding.ivCategoryItem.setImageResource(data.categoryImage)
        holder.binding.tvCategoryName.text = data.categoryName
    }
}