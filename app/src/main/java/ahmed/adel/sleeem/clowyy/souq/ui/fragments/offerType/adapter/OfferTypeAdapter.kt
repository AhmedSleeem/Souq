package ahmed.adel.sleeem.clowyy.souq.ui.fragments.offerType.adapter

import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.offerType.OfferTypeFragmentDirections
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.math.round

class OfferTypeAdapter(val context: Context) :
    RecyclerView.Adapter<OfferTypeAdapter.ViewHolder>() {

    private var items = arrayListOf<ProductResponse.Item>()

    fun changeData(newData:ArrayList<ProductResponse.Item>){
        val oldData = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(oldData, newData)
        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: ItemRecommendedRvBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(product : ProductResponse.Item ) = with(itemView) {
            Glide.with(context)
                .load(product.image)
                .fitCenter()
                .into(binding.imgProduct)

            round(3.554786)
            binding.tvProductName.text = product.title
            binding.tvOldCost.text = String.format("%.2f" , product.price) + "Egp"
            if(product.sale != null){
                val newPrice:Float  = (product.price * (1.0 - product.sale.amount.toFloat()/100)).toFloat()
                val num:Int=(newPrice*100).toInt()
                val  num2:Double = (num/100.0)
                Log.e("price = " , num2.toString())
                binding.tvCost.text = String.format("%.2f" , newPrice) + "Egp"
                binding.tvOffPercentage.text = (product.sale.duration .toString() +"%")
            }else{
                binding.tvCost.text = String.format("%.2f" , product.price) + "Egp"
                binding.tvOffPercentage.visibility = View.INVISIBLE
                binding.tvOldCost.visibility = View.INVISIBLE
            }

            setOnClickListener {
                val action = OfferTypeFragmentDirections.actionOfferTypeFragmentToDetailsFragment(product,null)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemRecommendedRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)= holder.bind(items[position])


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