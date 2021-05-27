package ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.bottomDialog.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemBottomFragmentCategoryRvBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class FilterBrandAdapter () :
    RecyclerView.Adapter<FilterBrandAdapter.ViewHolder>() {
    private var items = listOf<String>()
    var onItemClickListener:OnItemClickListener?=null

    fun changeData(newData: List<String>){
        val oldData = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )

        items = newData
        diffResult.dispatchUpdatesTo(this)

    }

    inner class ViewHolder(val binding: ItemBottomFragmentCategoryRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: String) = with(itemView) {
            binding.categoryTv.text = item

            if (onItemClickListener != null){
                setOnClickListener{
                    onItemClickListener!!.onClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBottomFragmentCategoryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(items[position])


    class ItemsDiffCallback(
        private val oldData: List<String>,
        private val newData: List<String>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
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

    interface OnItemClickListener{
        fun onClick(brand:String)
    }
}