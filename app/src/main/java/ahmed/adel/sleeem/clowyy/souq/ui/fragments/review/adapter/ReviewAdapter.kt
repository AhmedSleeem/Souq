package ahmed.adel.sleeem.clowyy.souq.ui.fragments.review.adapter


import ahmed.adel.sleeem.clowyy.souq.databinding.ItemReviewBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ReviewResponse
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ReviewAdapter(val context : Context) :
    RecyclerView.Adapter<ReviewAdapter.RepoViewHolder>() {

    var setOnItemClick:OnItemClick?=null
    private var items = ReviewResponse()

    fun changeData(newData: ReviewResponse, clearOldData:Boolean = false){
        if (clearOldData) {
            items = newData
            notifyDataSetChanged()
        }else{
            val oldData = items
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                ItemsDiffCallback(
                    oldData,
                    newData
                )
            )
            items = newData
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(items[position])



    inner class RepoViewHolder(var binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewResponse.Item) = with(itemView) {
            Glide.with(context)
                .load(item.user?.profileImage)
                .into( binding.profileReviewImageView)
            binding.usernameReviewTextView.text = item.user?.name ?: "no name"
            binding.ratingReviewBar.rating = (item.rating/2).toFloat()
            binding.reviewTextView.text = item.description

            if (setOnItemClick!=null) {
                setOnClickListener {
                    setOnItemClick!!.onClick(item)
                }
            }
        }
    }

    class ItemsDiffCallback(
        private val oldData:ArrayList<ReviewResponse.Item>,
        private val newData:ArrayList<ReviewResponse.Item>
    ): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition]._id == newData[newItemPosition]._id
        }

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return ((oldData[oldItemPosition]._id == newData[newItemPosition]._id) &&  (oldData[oldItemPosition].user == newData[newItemPosition].user))
        }

    }

    interface OnItemClick{
        fun onClick(item: ReviewResponse.Item)
    }
}