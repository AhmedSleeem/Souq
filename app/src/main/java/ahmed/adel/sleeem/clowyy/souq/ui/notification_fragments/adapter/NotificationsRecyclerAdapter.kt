package ahmed.adel.sleeem.clowyy.souq.ui.notification_fragments.adapter

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemNotificationsRvBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.ItemRecommendedRvBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.NotificationItem
import ahmed.adel.sleeem.clowyy.souq.pojo.SaleItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NotificationsRecyclerAdapter(
    private var items:MutableList<NotificationItem>,
private var isOffer:Boolean=false ,
private var isFeed:Boolean=false ,
private var isActivity:Boolean=false) : RecyclerView.Adapter<NotificationsRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemNotificationsRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemNotificationsRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        if (isOffer)
        holder.binding.notificationImg.setImageResource(R.drawable.ic_offer_blue)

        if (isFeed){
            holder.binding.notificationImg.setImageResource(R.drawable.shoes)
            holder.binding.notificationImg.layoutParams.height =120
            holder.binding.notificationImg.layoutParams.width =120
        }


        if (isActivity)
            holder.binding.notificationImg.setImageResource(R.drawable.ic_group)

        holder.binding.titleTv.text = data.title
        holder.binding.detailsTv.text = data.details
        holder.binding.dateTv.text = data.date



    }
}