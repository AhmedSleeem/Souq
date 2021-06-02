package ahmed.adel.sleeem.clowyy.souq.ui.fragments.notification

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentNotificationFeedBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.NotificationItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.notification.adapter.NotificationsRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class NotificationFeedFragment : Fragment() {

    private lateinit var binding: FragmentNotificationFeedBinding
    private lateinit var adapter: NotificationsRecyclerAdapter
    private val data = mutableListOf<NotificationItem>(
        NotificationItem(title = "New Product" , details = "Nike Air Zoom Pegasus 36 Miami - Special For your Activity", date = "April 30, 2014 1:01 PM"),
        NotificationItem(title = "Best Product" , details = "Nike Air Zoom Pegasus 36 Miami - Special For your Activity", date = "April 30, 2014 1:01 PM"),
        NotificationItem(title = "New Product" , details = "Nike Air Zoom Pegasus 36 Miami - Special For your Activity", date = "April 30, 2014 1:01 PM")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        adapter = NotificationsRecyclerAdapter(data,isFeed = true)
        binding.feedRv.adapter = adapter
    }

}