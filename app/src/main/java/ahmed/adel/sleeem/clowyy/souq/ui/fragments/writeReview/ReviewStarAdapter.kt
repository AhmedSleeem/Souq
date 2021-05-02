package ahmed.adel.sleeem.clowyy.souq.ui.fragments.writeReview


import ahmed.adel.sleeem.clowyy.souq.databinding.ItemReviewStarBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.StarItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class ReviewStarAdapter(val listener: (View, StarItem, Int) -> Unit) :
    RecyclerView.Adapter<ReviewStarAdapter.RepoViewHolder>() {
    private var data: MutableList<StarItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemReviewStarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: MutableList<StarItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getRepoAt(position: Int): StarItem? {
        return data.get(position)
    }

    inner class RepoViewHolder(var binding: ItemReviewStarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StarItem) = with(itemView) {
            binding.starNumTextView.text = item.starNum.toString()

            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}