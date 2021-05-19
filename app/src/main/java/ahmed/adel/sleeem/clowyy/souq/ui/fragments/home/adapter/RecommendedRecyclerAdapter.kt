package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ItemResponse
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecommendedRecyclerAdapter(val context:Context) : RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder>() {
    private var items = arrayListOf<ItemResponse.ItemResponseItem>()

    fun changeData(newData:ArrayList<ItemResponse.ItemResponseItem>){
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

    var itemClickListner : ItemClickListner? = null

    inner class ViewHolder(val binding: ItemRecommendedRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind( item : ItemResponse.ItemResponseItem ) = with(itemView){

            Glide.with(context)
                .load(item.image)
                .fitCenter()
                .into(binding.imgProduct)

            binding.tvProductName.text = item.title
            binding.tvCost.text = (item.price - 5).toString()
            binding.tvOldCost.text = item.price.toString()
            binding.tvOffPercentage.text = "5%"
            binding.ratingBar.rating = (item.rating/2).toFloat()

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items.get(position))

    class ItemsDiffCallback(
        private val oldData:ArrayList<ItemResponse.ItemResponseItem>,
        private val newData:ArrayList<ItemResponse.ItemResponseItem>
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
    interface ItemClickListner{
        fun onClick(view : View)
    }
}

