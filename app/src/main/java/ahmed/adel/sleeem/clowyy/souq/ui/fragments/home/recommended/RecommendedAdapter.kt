package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.recommended

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecommendedAdapter: PagingDataAdapter<ProductResponse.Item, RecommendedAdapter.ViewHolder>(
    COMPARATOR
) {

    var itemClickListener : ItemClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecommendedRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    inner class ViewHolder(val binding:ItemRecommendedRvBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(product : ProductResponse.Item ) = with(itemView){

            Glide.with(context)
                .load(product.image)
                .fitCenter()
                .into(binding.imgProduct)

            binding.tvProductName.text = product.title
            binding.tvOldCost.text = String.format("%.2f", product.price) + " Egp"
            if(product.sale != null){
                val newPrice : Float = (product.price * (1.0 - product.sale.amount.toFloat()/100)).toFloat()
                binding.tvCost.text = String.format("%.2f", newPrice) + " Egp"
                binding.tvOffPercentage.text = (product.sale.amount .toString() +"%")
            }else{
                binding.tvCost.text = String.format("%.2f", product.price) + " Egp"
                binding.tvOffPercentage.visibility = View.INVISIBLE
                binding.tvOldCost.visibility = View.INVISIBLE
            }
            binding.ratingBar.rating = (product.rating/2.0f).toFloat()

            if(itemClickListener != null) {
                setOnClickListener {
                    itemClickListener!!.onClick(it,product)
                }
            }

        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ProductResponse.Item>() {
            override fun areItemsTheSame(oldItem: ProductResponse.Item, newItem: ProductResponse.Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProductResponse.Item, newItem: ProductResponse.Item): Boolean =
                oldItem.id == newItem.id
        }
    }

    interface ItemClickListener{
        fun onClick(view: View, item: ProductResponse.Item)
    }

    }
