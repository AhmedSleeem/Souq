package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecommendedRecyclerAdapter(val context:Context) : RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder>() {
    private var items = arrayListOf<ProductResponse.Item>()

    fun changeData(newData:ArrayList<ProductResponse.Item>){
        val oldData = items
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    var itemClickListener : ItemClickListener? = null

    inner class ViewHolder(val binding: ItemRecommendedRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product : ProductResponse.Item ) = with(itemView){

            Glide.with(context)
                .load(product.image)
                .fitCenter()
                .into(binding.imgProduct)

            binding.tvProductName.text = product.title
            binding.tvOldCost.text = product.price.toString()+" Egp"
            if(product.sale != null){
                val newPrice : Float = (product.price * (1.0 - product.sale.amount.toFloat()/100)).toFloat()
                Log.e("price = " , newPrice.toString())
                binding.tvCost.text = newPrice .toString() + " Egp"
                binding.tvOffPercentage.text = (product.sale.duration .toString() +"%")
            }else{
                binding.tvCost.text = product.price.toString()+" Egp"
                binding.tvOffPercentage.visibility = View.INVISIBLE
                binding.tvOldCost.visibility = View.INVISIBLE
            }
            binding.ratingBar.rating = (product.rating/2.0f)

            if(itemClickListener != null) {
                setOnClickListener {
                    itemClickListener!!.onClick(it,product)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items.get(position))

    class ItemsDiffCallback(
        private val oldData:ArrayList<ProductResponse.Item>,
        private val newData:ArrayList<ProductResponse.Item>
    ): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
           return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }
    interface ItemClickListener{
        fun onClick(view: View, item: ProductResponse.Item)
    }
}

