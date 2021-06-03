package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.HomeFragmentDirections
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class SaleRecyclerAdapter(val context:Context) :
    RecyclerView.Adapter<SaleRecyclerAdapter.ViewHolder>() {
    private var items = mutableListOf<ProductResponse.Item>()
    fun changeData(newData:List<ProductResponse.Item>){
        val oldData = items
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback( oldData, newData)
        )
        for(iterate in 0..5){
            items.add(newData[iterate])
        }
        //  items = newData.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
    inner class ViewHolder(val binding: ItemSaleRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product : ProductResponse.Item ) = with(itemView){
            Glide.with(context)
                .load(product.image)
                .fitCenter()
                .into(binding.imgProductSaleIv)
            binding.productNameSaleTc.text = product.title
            binding.oldCostSaleTv.text = String.format("%.2f", product.price) + " Egp"
            if(product.sale != null){
                val newPrice:Float  = (product.price * (1.0 - product.sale.amount.toFloat()/100)).toFloat()
                binding.costSaleTv.text =String.format("%.2f", newPrice) + " Egp"
                binding.offPercentageSaleTv.text = (product.sale.amount.toString() +"%")
            }else{
                binding.costSaleTv.text = String.format("%.2f", product.price) + " Egp"
                binding.offPercentageSaleTv.visibility = View.INVISIBLE
                binding.oldCostSaleTv.visibility = View.INVISIBLE
            }

            setOnClickListener {
//                Navigation.findNavController(it)
//                    .navigate(R.id.action_homeFragment_to_detailsFragment)
//                val itemDetails = items[position]
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(product,null)
                it.findNavController().navigate(action)
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
        private val oldData: MutableList<ProductResponse.Item>,
        private val newData: List<ProductResponse.Item>
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