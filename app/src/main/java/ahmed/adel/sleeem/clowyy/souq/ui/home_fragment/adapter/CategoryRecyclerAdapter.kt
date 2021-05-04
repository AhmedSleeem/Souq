package ahmed.adel.sleeem.clowyy.souq.ui.home_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CategoryRecyclerAdapter(private var images:IntArray) : RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.iv_category_item);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category_rv, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
    }
}