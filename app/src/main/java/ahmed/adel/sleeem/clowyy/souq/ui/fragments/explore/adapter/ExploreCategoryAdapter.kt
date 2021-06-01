package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemExploreCategoryBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.CategoryResponse
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ExploreCategoryAdapter(val context:Context) :
    RecyclerView.Adapter<ExploreCategoryAdapter.ViewHolder>() {

    var setOnItemClickListener:OnItemClickListener?=null

    var items = arrayListOf<CategoryResponse.CategoryResponseItem>()

    fun changeData(newData:ArrayList<CategoryResponse.CategoryResponseItem>){
        val oldData = items
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ExploreCategoryAdapter.ItemsDiffCallback(
                oldData,
                newData
            )
        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: ItemExploreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind( item : CategoryResponse.CategoryResponseItem) = with(itemView) {
                Glide.with(context)
                    .load(item.url)
                    .into(binding.ivCategoryItem)
                binding.tvCategoryName.text = item.name

                if (setOnItemClickListener != null){
                    setOnClickListener {
                       setOnItemClickListener!!.onClick(it,item)
                    }
                }
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemExploreCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    class ItemsDiffCallback(
        private val oldData:ArrayList<CategoryResponse.CategoryResponseItem>,
        private val newData:ArrayList<CategoryResponse.CategoryResponseItem>
    ): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].name == newData[newItemPosition].name
        }

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].url ==  newData[newItemPosition].url
        }

    }

    interface OnItemClickListener{
        fun onClick(
            view: View,
            item: CategoryResponse.CategoryResponseItem
        );
    }
}