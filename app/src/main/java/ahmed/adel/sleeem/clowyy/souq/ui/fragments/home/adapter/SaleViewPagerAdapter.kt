package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView


class SaleViewPagerAdapter(private var images: IntArray) : RecyclerView.Adapter<SaleViewPagerAdapter.ViewHolder>(){

    public fun changeData(images: IntArray){
        this.images = images
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.imageView);
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sale_viewpager, parent, false))
    }

    override fun getItemCount(): Int {
       return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position]);
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailsFragment)
        }
    }

}