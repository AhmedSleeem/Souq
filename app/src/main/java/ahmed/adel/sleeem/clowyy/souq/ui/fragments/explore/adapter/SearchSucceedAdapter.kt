package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class SearchSucceedAdapter (private var items: MutableList<SaleItem>) :
    RecyclerView.Adapter<SearchSucceedAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRecommendedRvBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind( item : SaleItem ) = with(itemView){
            binding.imgProduct.setImageResource(item.image)
            binding.tvProductName.text = item.name
            binding.tvCost.text = item.newPrice.toString()
            binding.tvOldCost.text = item.price.toString()
            binding.tvOffPercentage.text = item.salePercent.toString()

            setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_searchSucceedFragment_to_detailsFragment)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemRecommendedRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
}