package ahmed.adel.sleeem.clowyy.souq.ui.fragments.review.adapter


import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemReviewStarBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class ReviewStarAdapter() :
    RecyclerView.Adapter<ReviewStarAdapter.RepoViewHolder>() {
    private var selectedRow = -1;
    var setOnItemClick: OnItemClick?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemReviewStarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(position)



    inner class RepoViewHolder(var binding: ItemReviewStarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) = with(itemView) {
            binding.starNumTextView.text = (position+1).toString()
            binding.view.background = resources.getDrawable(R.drawable.register_custom_edittext_shape)
            if (setOnItemClick != null){
                setOnClickListener {
                    setOnItemClick!!.onClick((position+1)*2)
                    if (selectedRow!=-1 && selectedRow!=position)
                        notifyItemChanged(selectedRow)
                    binding.view.background = resources.getDrawable(R.drawable.filter_btn_selected_shape)
                    selectedRow=position
                }
            }
        }
    }

    interface OnItemClick{
        fun onClick(rate:Int)
    }
}