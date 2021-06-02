package ahmed.adel.sleeem.clowyy.souq.ui.fragments.notification

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentNotificationActivityBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.NotificationItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.notification.adapter.NotificationsRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class NotificationActivityFragment : Fragment() {
    private lateinit var binding: FragmentNotificationActivityBinding
    private lateinit var adapter: NotificationsRecyclerAdapter
    private val data = mutableListOf<NotificationItem>(
        NotificationItem(title = "Transaction Nike Air Zoom Product" , details = "Culpa cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor", date = "April 30, 2014 1:01 PM"),
        NotificationItem(title = "Transaction Nike Air Zoom Pegasus 36 Miami" , details = "Culpa cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo", date = "April 30, 2014 1:01 PM"),
        NotificationItem(title = "Transaction Nike Air Max ", details = "Culpa cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo", date = "April 30, 2014 1:01 PM")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        adapter = NotificationsRecyclerAdapter(data,isActivity = true)
        binding.activityRv.adapter=adapter
    }


}