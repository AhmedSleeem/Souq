package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemExploreCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ExplorerItem
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class ExploreCategoryAdapter(private var items: MutableList<ExplorerItem>) :
    RecyclerView.Adapter<ExploreCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemExploreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind( item : ExplorerItem) = with(itemView) {
                binding.ivCategoryItem.setImageResource(item.categoryImage)
                binding.tvCategoryName.text = item.categoryName

                setOnClickListener {
                    Navigation.findNavController(it).navigate(R.id.action_exploreFragment_to_searchSucceedFragment)
                }
            }
        }

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

    override fun onBindViewHolder(holder: ExploreCategoryAdapter.ViewHolder, position: Int) = holder.bind(items[position])
}