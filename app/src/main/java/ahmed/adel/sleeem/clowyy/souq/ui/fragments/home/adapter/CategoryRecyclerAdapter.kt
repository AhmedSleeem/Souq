package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemCategoryRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CategoryRecyclerAdapter(private var data:List<ExplorerItem> ): RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCategoryRvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(item:ExplorerItem) = with(itemView){
            binding.ivCategoryItem.setImageResource(item.categoryImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(data[position])

}