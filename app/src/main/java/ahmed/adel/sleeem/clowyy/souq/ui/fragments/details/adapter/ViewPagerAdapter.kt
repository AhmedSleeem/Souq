package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ViewpagerItemBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ViewPagerAdapter(val context: Context) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    private var data = mutableListOf<String>()

    fun changeData(newData: List<String>) {
        val oldData = data
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )
        data = newData as MutableList<String>
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ViewpagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: String) = with(itemView) {
            Glide.with(context)
                .load(product)
                .centerCrop()
                .into(binding.imageView)

            setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewpagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    class ItemsDiffCallback(
        private val oldData: MutableList<String>,
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
}

