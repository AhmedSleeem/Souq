package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.recommended

import ahmed.adel.sleeem.clowyy.souq.databinding.RecommendedPageStateBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class RecommendedStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<RecommendedStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ViewHolder(
            RecommendedPageStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )

    class ViewHolder(
        private val binding: RecommendedPageStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {


            }

            if(loadState is LoadState.Error){
                if (!loadState.error.localizedMessage.isNullOrEmpty()) {
                    retryBtn.isVisible = loadState is LoadState.Error
                    ivError.isVisible = loadState is LoadState.Error
                }else{
                    retryBtn.visibility= View.GONE
                    ivError.visibility= View.GONE
                }
            }

            if (loadState is LoadState.Loading) {
                progress.isVisible = loadState is LoadState.Loading
                retryBtn.visibility= View.GONE
                ivError.visibility= View.GONE
            }

        }
    }
}