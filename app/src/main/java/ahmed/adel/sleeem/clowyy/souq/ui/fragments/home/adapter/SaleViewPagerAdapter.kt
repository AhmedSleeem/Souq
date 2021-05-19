package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleViewpagerBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ItemResponse
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class SaleViewPagerAdapter(val context:Context) : RecyclerView.Adapter<SaleViewPagerAdapter.ViewHolder>(){
    private var data = arrayListOf<ItemResponse.ItemResponseItem>()


    fun changeData(newData: ArrayList<ItemResponse.ItemResponseItem>){
        val oldData = data
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemSaleViewpagerBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemResponse.ItemResponseItem) = with(itemView) {

            Glide.with(context)
                .load(item.image)
                .centerCrop()
                .into(binding.imageView)

            setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailsFragment)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSaleViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)=holder.bind(data[position])

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
}