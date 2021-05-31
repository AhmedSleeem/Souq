package ahmed.adel.sleeem.clowyy.souq.ui.fragments.details.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SizeRecyclerAdapter (private var sizeArr:List<String>) : RecyclerView.Adapter<SizeRecyclerAdapter.ViewHolder>() {

    var row_index : Int? = null
    var setOnItemClickListner : ClckListner? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView.findViewById<TextView>(R.id.size_txt);
        var row_linearlayout = itemView.findViewById<LinearLayout>(R.id.size_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_size_rv, parent, false))
    }

    override fun getItemCount(): Int {

        return sizeArr.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.setText(sizeArr[position])

        if(setOnItemClickListner != null){
            holder.row_linearlayout.setOnClickListener(View.OnClickListener {
                row_index = position
                setOnItemClickListner!!.clickListner(sizeArr[position])
                notifyDataSetChanged()
            })
            if (row_index === position) {
                holder.row_linearlayout.setBackgroundResource(R.drawable.iv_size_item_selected)
            } else {
                holder.row_linearlayout.setBackgroundResource(R.drawable.iv_size_item_background)
            }
        }



    }
    interface ClckListner{
        fun clickListner(itemSize : String)
    }

}

