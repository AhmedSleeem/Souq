package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.saleProducts

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.HomeFragmentDirections
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SaleRecyclerAdapter: PagingDataAdapter<ProductResponse.Item, SaleRecyclerAdapter.ViewHolder>(
    COMPARATOR
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSaleRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    inner class ViewHolder(val binding:ItemSaleRvBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductResponse.Item, position: Int) = with(itemView) {

            Glide.with(binding.imgProductSaleIv)
                .load(item.image)
                .fitCenter()
                .into(binding.imgProductSaleIv)

            binding.productNameSaleTc.text = item.title
            binding.oldCostSaleTv.text = String.format("%.2f", item.price) + " Egp"

            if(item.sale != null){
                val newPrice:Float  = (item.price * (1.0 - item.sale.amount.toFloat()/100)).toFloat()
                binding.costSaleTv.text =String.format("%.2f", newPrice) + " Egp"
                binding.offPercentageSaleTv.text = (item.sale.amount.toString() +"%")
            }else{
                binding.costSaleTv.text = String.format("%.2f", item.price) + " Egp"
                binding.offPercentageSaleTv.visibility = View.INVISIBLE
                binding.oldCostSaleTv.visibility = View.INVISIBLE
            }

            setOnClickListener {
//                Navigation.findNavController(it)
//                    .navigate(R.id.action_homeFragment_to_detailsFragment)
//                val itemDetails = items[position]
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item,null)
                it.findNavController().navigate(action)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ProductResponse.Item>() {
            override fun areItemsTheSame(oldItem: ProductResponse.Item, newItem: ProductResponse.Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProductResponse.Item, newItem: ProductResponse.Item): Boolean =
                oldItem.id == newItem.id
        }
    }

    }
