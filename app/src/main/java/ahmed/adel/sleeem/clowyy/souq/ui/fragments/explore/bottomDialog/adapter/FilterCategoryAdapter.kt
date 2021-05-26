package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemBottomFragmentCategoryRvBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FilterCategoryAdapter () :
    RecyclerView.Adapter<FilterCategoryAdapter.ViewHolder>() {

    private var items = listOf<Pair<String,Int>>()
    var onItemClickListener: OnItemClickListener?=null

    fun changeData(newData: List<Pair<String,Int>>) {
        val oldData = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            FilterCategoryAdapter.ItemsDiffCallback(
                oldData,
                newData
            )
        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(val binding: ItemBottomFragmentCategoryRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(item: Pair<String, Int>) = with(itemView) {
            binding.categoryTv.text = item.first+" ( "+item.second+" )"
            if (onItemClickListener != null){
                setOnClickListener{
                    onItemClickListener!!.onClick(item.first)
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
        private val oldData: List<Pair<String,Int>>,
        private val newData: List<Pair<String,Int>>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].first == newData[newItemPosition].first
        }

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].first == newData[newItemPosition].first
        }
    }

    interface OnItemClickListener{
        fun onClick(category:String)
    }
}