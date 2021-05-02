package ahmed.adel.sleeem.clowyy.souq.ui.details_fragment.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.ui.home_fragment.adapter.CategoryRecyclerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class ColorRecylerAdapter (private var colors:IntArray) : RecyclerView.Adapter<ColorRecylerAdapter.ViewHolder>() {

    var row_index : Int? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.color_image);
        var row_linearlayout = itemView.findViewById<RelativeLayout>(R.id.color_layout)
        var imageView1 = itemView.findViewById<ImageView>(R.id.color_image1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_color_rv, parent, false))
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(colors[position])


        holder.row_linearlayout.setOnClickListener(View.OnClickListener {
            row_index = position
            notifyDataSetChanged()
        })
        if (row_index === position){
            holder.imageView1.isVisible = true
        } else {
            holder.imageView1.isVisible = false
        }


    }
}
