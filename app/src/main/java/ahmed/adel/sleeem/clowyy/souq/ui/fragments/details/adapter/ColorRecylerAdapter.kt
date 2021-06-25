package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class ColorRecylerAdapter (private var colors:List<String> ,val context : Context) : RecyclerView.Adapter<ColorRecylerAdapter.ViewHolder>() {

    var row_index : Int? = null
    var setOnItemClickListner : ColorRecylerAdapter.ClckListner? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<View>(R.id.color_image)
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
//        val drawable = AppCompatResources.getDrawable(context,R.drawable.circle_shape)
//        val com = DrawableCompat.wrap(drawable!!)
//        DrawableCompat.setTint(com,Color.parseColor(colors[position]))
        if (!colors.isEmpty()){
            holder.imageView.setBackgroundColor(Color.parseColor(colors[position]))
        }else{
            holder.imageView.setBackgroundColor(Color.BLUE)
        }



        if(setOnItemClickListner !=null){
            holder.row_linearlayout.setOnClickListener(View.OnClickListener {
                row_index = position
                setOnItemClickListner!!.clickListner(colors[position])
                notifyDataSetChanged()
            })
            if (row_index === position){
                holder.imageView1.isVisible = true
            } else {
                holder.imageView1.isVisible = false
            }
        }

    }

    interface ClckListner{
        fun clickListner(itemColor : String)
    }
}