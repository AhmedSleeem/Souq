package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemCategoryListRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.CategoryResponse
import ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment.ListCategoryFragmentDirections
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.SearchSucceedFragment

import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.category.CategoryRecyclerAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListCategoryAdapter (val context : Context) :
    RecyclerView.Adapter<ListCategoryAdapter.ViewHolder>() {



    private var items = arrayListOf<CategoryResponse.CategoryResponseItem>()
    fun changeData(newData:ArrayList<CategoryResponse.CategoryResponseItem>) {
        val oldData = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            CategoryRecyclerAdapter.ItemsDiffCallback(
                oldData,
                newData
            )

        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemCategoryListRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: CategoryResponse.CategoryResponseItem) = with(itemView) {
            Glide.with(context)
                .load(item.url)
                .fitCenter()
                .into(binding.imgProduct)

            binding.tvProduct.text = item.name

            setOnClickListener {
                val action = ListCategoryFragmentDirections.actionListCategoryFragmentToSearchSucceedFragment(query = item.name,searchStatus = SearchSucceedFragment.SearchStatus.CATEGORY)
                it.findNavController().navigate(action)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryListRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(items.get(position))


    class ItemsDiffCallback(
        private val oldData: ArrayList<CategoryResponse.CategoryResponseItem>,
        private val newData: ArrayList<CategoryResponse.CategoryResponseItem>
    ) : DiffUtil.Callback() {
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
            return oldData[oldItemPosition] == newData[newItemPosition]
        }
    }

}