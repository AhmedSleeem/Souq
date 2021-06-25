package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemCartBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import ahmed.adel.sleeem.clowyy.souq.room.cart.Cart
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CartAdapter(var context : Context) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var _viewBinding : ItemCartBinding
    val viewBinding : ItemCartBinding get() = _viewBinding
    private var items = listOf<Cart>()
    var setOnItemClickListner : ItemClickListener? = null
    var setOnCountClickListner : CountClickListner? = null

    fun changeData(newData:List<Cart>, clearOldData:Boolean = false){
        if (clearOldData) {
            items = newData
            notifyDataSetChanged()
        }else {
            val oldData = this.items
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                CartAdapter.ItemsDiffCallback(
                    oldData,
                    newData
                )
            )
            this.items = newData
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        _viewBinding =   ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(
            _viewBinding
        )
    }

    override fun getItemCount() = this.items.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(this.items[position] , position)

//    fun swapData(data: MutableList<CartItem>) {
//        this.items = data
//        notifyDataSetChanged()
//    }
//
//    fun getRepoAt(position: Int): CartItem? {
//        return this.items.get(position)
//    }

    inner class CartViewHolder(var binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Cart,
            position: Int
        ) = with(itemView) {
            binding.descCartTextView.text = item.itemName
//            binding.itemQuantity.text = item.quantity.toString()
            binding.priceCartTextView.text = item.price.toString() +" Egp"
            binding.countCartTextView.text = item.count.toString()
            Glide.with(context)
                .load(item.itemImage)
                .fitCenter()
                .placeholder(R.drawable.ic_logo)
                .into(binding.itemCartImageView)

            if(setOnItemClickListner != null) {
                binding.deleteItemBtn.setOnClickListener{
                        setOnItemClickListner!!.onClick(it, item, position)

                        Log.e("TAG", "delete", )
                    }
                }

                binding.addBtn.setOnClickListener {
                    if(item.count < item.count+10)
                        items[position].count++
                    if(setOnCountClickListner != null){
                        setOnCountClickListner!!.onClick(item)
                    }
                    notifyItemChanged(position)
                }

                binding.minusBtn.setOnClickListener {
                    if(item.count > 0)
                        items[position].count--
                    if(setOnCountClickListner != null){
                        setOnCountClickListner!!.onClick(item)
                    }
                    notifyItemChanged(position)
                }






        }
    }


    class ItemsDiffCallback(
        private val oldData:List<Cart>,
        private val newData:List<Cart>
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
            return (oldData[oldItemPosition].id == newData[newItemPosition].id)
                    && (oldData[oldItemPosition].count == newData[newItemPosition].count)
        }

    }

    interface ItemClickListener{
        fun onClick(view: View, item: Cart, position: Int)
    }
    interface CountClickListner{
        fun onClick(item: Cart)
    }

}