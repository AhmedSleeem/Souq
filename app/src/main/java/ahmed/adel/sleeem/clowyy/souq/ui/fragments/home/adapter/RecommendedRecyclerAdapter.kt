package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class RecommendedRecyclerAdapter(private var items:MutableList<SaleItem>) : RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder>() {

    var itemClickListner : ItemClickListner? = null

    inner class ViewHolder(val binding: ItemRecommendedRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind( item : SaleItem ) = with(itemView){
            binding.imgProduct.setImageResource(item.image)
            binding.tvProductName.text = item.name
            binding.tvCost.text = item.newPrice.toString()
            binding.tvOldCost.text = item.price.toString()
            binding.tvOffPercentage.text = item.salePercent.toString()
            binding.ratingBar.rating = 4.0f

            if(itemClickListner != null) {
                setOnClickListener {
                    itemClickListner!!.onClick(it)
                }
            }

            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemRecommendedRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    interface ItemClickListner{
        fun onClick(view : View)
    }
}

