package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecommendedRecyclerAdapter(private var items:ItemResponse ,val context:Context) : RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder>() {

    var itemClickListner : ItemClickListner? = null

    inner class ViewHolder(val binding: ItemRecommendedRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind( item : ItemResponse.ItemResponseItem ) = with(itemView){

            Glide.with(context)
                .load(item.image)
                .fitCenter()
                .into(binding.imgProduct)
            binding.tvProductName.text = item.title
            binding.tvCost.text = (item.price - 5).toString()
            binding.tvOldCost.text = item.price.toString()
            binding.tvOffPercentage.text = "5%"
            binding.ratingBar.rating = 4.0f

            if(itemClickListner != null) {
                setOnClickListener {
                    itemClickListner!!.onClick(it)
                }
            }

            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemRecommendedRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    interface ItemClickListner{
        fun onClick(view : View)
    }
}

