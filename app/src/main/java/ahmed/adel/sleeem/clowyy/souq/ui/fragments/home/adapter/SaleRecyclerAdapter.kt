package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class SaleRecyclerAdapter(private var items: MutableList<SaleItem>) :
    RecyclerView.Adapter<SaleRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSaleRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind( item : SaleItem ) = with(itemView){
            binding.imgProductSaleIv.setImageResource(item.image)
            binding.productNameSaleTc.text = item.name
            binding.costSaleTv.text = item.newPrice.toString()
            binding.oldCostSaleTv.text = item.price.toString()
            binding.offPercentageSaleTv.text = item.salePercent.toString()

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
}