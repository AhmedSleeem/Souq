package ahmed.adel.sleeem.clowyy.souq.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.model.FavouriteItem
import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class FavouriteAdapter(var context: Context, var arrayList: ArrayList<FavouriteItem>) :
    BaseAdapter() {


    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View = View.inflate(context, R.layout.card_view_item_grid, null)
        var productImage: ImageView = view.findViewById(R.id.img_product)
        var productName: TextView = view.findViewById(R.id.tv_product_name)
        var productCost: TextView = view.findViewById(R.id.tv_cost)
        var productOldCost: TextView = view.findViewById(R.id.tv_old_cost)
        var offerPercentage: TextView = view.findViewById(R.id.tv_off_percentage)


        var listItem: FavouriteItem = arrayList.get(position)

        productImage.setImageResource(listItem.productImage!!)
        productName.text = listItem.productName
        productCost.text = listItem.productCost
        productOldCost.text = listItem.productOldCost
        offerPercentage.text = listItem.offerPercentage

        productOldCost.setPaintFlags(productOldCost.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        return view
    }
}