package ahmed.adel.sleeem.clowyy.souq.ui.fragments.address


import ahmed.adel.sleeem.clowyy.souq.databinding.ItemShipToBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.AddressItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class AddressAdapter(val listener: (View, AddressItem, Int) -> Unit) :
    RecyclerView.Adapter<AddressAdapter.RepoViewHolder>() {
    private var data: MutableList<AddressItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemShipToBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: MutableList<AddressItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getRepoAt(position: Int): AddressItem? {
        return data.get(position)
    }

    inner class RepoViewHolder(var binding: ItemShipToBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AddressItem) = with(itemView) {
            binding.textView17.text = item.itemDesc


            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}