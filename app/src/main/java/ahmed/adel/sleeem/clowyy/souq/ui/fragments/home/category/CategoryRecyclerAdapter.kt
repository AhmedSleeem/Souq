package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.category

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemCategoryRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.CategoryResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.explore.SearchSucceedFragment
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.HomeFragmentDirections
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryRecyclerAdapter(val context :Context): RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {
    private var items = arrayListOf<CategoryResponse.CategoryResponseItem>()
    fun changeData(newData:ArrayList<CategoryResponse.CategoryResponseItem>) {
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

        class ViewHolder(val binding: ItemCategoryRvBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun binding(item: CategoryResponse.CategoryResponseItem) = with(itemView) {
                Glide.with(context)
                    .load(item.url)
                    .fitCenter()
                    .into(binding.ivCategoryItem)

                setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToSearchSucceedFragment(query =  item.name,searchStatus = SearchSucceedFragment.SearchStatus.CATEGORY)
                    it.findNavController().navigate(action)
                }

            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemCategoryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        override fun getItemCount(): Int {
            if(items.size > 5){
                return 5
            }else
                return items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.binding(items.get(position))


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