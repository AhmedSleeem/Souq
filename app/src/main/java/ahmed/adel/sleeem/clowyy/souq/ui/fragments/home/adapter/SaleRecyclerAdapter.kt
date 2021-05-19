package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SaleRecyclerAdapter(val context:Context) :
    RecyclerView.Adapter<SaleRecyclerAdapter.ViewHolder>() {

    private var items = arrayListOf<ProductResponse.Item>()

    fun changeData(newData:ArrayList<ProductResponse.Item>){
        val oldData = items
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback( oldData, newData)
        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: ItemSaleRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product : ProductResponse.Item ) = with(itemView){

            Glide.with(context)
                .load(product.image)
                .fitCenter()
                .into(binding.imgProductSaleIv)

            binding.productNameSaleTc.text = product.title
            binding.costSaleTv.text = product.price.toString()
            binding.oldCostSaleTv.text = product.price.toString()
            binding.offPercentageSaleTv.text = "5%"

            setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailsFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSaleRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

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
}