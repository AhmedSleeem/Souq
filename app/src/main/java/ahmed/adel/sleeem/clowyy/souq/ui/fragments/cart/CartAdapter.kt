package ahmed.adel.sleeem.clowyy.souq.ui.fragments.cart


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemCartBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import android.content.Context
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
    private var items = arrayListOf<ProductResponse.Item>()
    var setOnItemClickListner : ItemClickListener? = null
    var setOnCountClickListner : CountClickListner? = null

    fun changeData(newData:ArrayList<ProductResponse.Item>, clearOldData:Boolean = false){
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
            item: ProductResponse.Item,
            position: Int
        ) = with(itemView) {
            binding.descCartTextView.text = item.title
            binding.itemQuantity.text = item.quantity.toString()
            binding.priceCartTextView.text = item.price.toString() +" Egp"
            binding.countCartTextView.text = item.countOfSelectedItem.toString()
            Glide.with(context)
                .load(item.image)
                .fitCenter()
                .placeholder(R.drawable.ic_logo)
                .into(binding.itemCartImageView)


                binding.deleteItemBtn.setOnClickListener{
                    if(setOnItemClickListner != null)
                        setOnItemClickListner!!.onClick(it , item , position)
                }

                binding.addBtn.setOnClickListener {
                    if(item.countOfSelectedItem < item.quantity)
                        items[position].countOfSelectedItem++
                    if(setOnCountClickListner != null){
                        setOnCountClickListner!!.onClick()
                    }
                    notifyItemChanged(position)
                }

                binding.minusBtn.setOnClickListener {
                    if(item.countOfSelectedItem > 0)
                        items[position].countOfSelectedItem--
                    if(setOnCountClickListner != null){
                        setOnCountClickListner!!.onClick()
                    }
                    notifyItemChanged(position)
                }






        }
    }


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
            return (oldData[oldItemPosition].id == newData[newItemPosition].id) && (oldData[oldItemPosition].quantity == newData[newItemPosition].quantity)
        }

    }

    interface ItemClickListener{
        fun onClick(view: View, item: ProductResponse.Item, position: Int)
    }
    interface CountClickListner{
        fun onClick()
    }

}