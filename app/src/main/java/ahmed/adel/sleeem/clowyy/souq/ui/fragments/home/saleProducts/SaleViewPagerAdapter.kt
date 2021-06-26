package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.saleProducts

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemSaleViewpagerBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.HomeFragmentDirections
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class SaleViewPagerAdapter(val context:Context) : RecyclerView.Adapter<SaleViewPagerAdapter.ViewHolder>(){
    private var data = mutableListOf<ProductResponse.Item>()


    fun changeData(newData: List<ProductResponse.Item>){
        val oldData = data
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )
        data = newData as MutableList<ProductResponse.Item>
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemSaleViewpagerBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductResponse.Item) = with(itemView){

            Glide.with(context)
                .load(product.image)
                .centerCrop()
                .into(binding.imageView)

            binding.saleTitleVp.text = product.sale!!.type
            setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(product,null)
                it?.findNavController()?.navigate(action)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSaleViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        if(data.size > 5) return 5
        else return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

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