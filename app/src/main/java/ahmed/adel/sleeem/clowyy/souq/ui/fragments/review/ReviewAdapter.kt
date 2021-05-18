package ahmed.adel.sleeem.clowyy.souq.ui.fragments.review


import ahmed.adel.sleeem.clowyy.souq.databinding.ItemReviewBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.ReviewItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class ReviewAdapter(val listener: (View, ReviewItem, Int) -> Unit?) :
    RecyclerView.Adapter<ReviewAdapter.RepoViewHolder>() {

    private var data: MutableList<ReviewItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(data[position])

    fun changeData(data: MutableList<ReviewItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getRepoAt(position: Int): ReviewItem? {
        return data.get(position)
    }

    inner class RepoViewHolder(var binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewItem) = with(itemView) {
            binding.profileReviewImageView.setImageResource(item.profileImage)
            binding.usernameReviewTextView.text = item.username
            binding.ratingReviewBar.rating = item.rating
            binding.reviewTextView.text = item.review
            binding.imageView6.setImageResource(item.img1)
            binding.imageView7.setImageResource(item.img2)
            binding.imageView8.setImageResource(item.img3)
            binding.dateReviewTextView.text = item.date

            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}