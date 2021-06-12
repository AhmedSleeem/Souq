package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.saleProducts

import ahmed.adel.sleeem.clowyy.souq.databinding.SalePageStateBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class SaleStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<SaleStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ViewHolder(
            SalePageStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )

    class ViewHolder(
        private val binding: SalePageStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {


            }

            if(loadState is LoadState.Error){
                if (!loadState.error.localizedMessage.isNullOrEmpty()) {
                    retryButton.isVisible = loadState is LoadState.Error
                    ivSearchError.isVisible = loadState is LoadState.Error
                }else{
                    retryButton.visibility= View.GONE
                    ivSearchError.visibility= View.GONE
                }
            }


            if(loadState is LoadState.Loading) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.visibility = View.GONE
                ivSearchError.visibility = View.GONE
            }

        }
    }
}