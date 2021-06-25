package ahmed.adel.sleeem.clowyy.souq.ui.fragments.notification

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentNotficationOfferBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.NotificationItem
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.notification.adapter.NotificationsRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class NotificationOfferFragment : Fragment() {

    private lateinit var binding:FragmentNotficationOfferBinding
    private lateinit var adapter:NotificationsRecyclerAdapter
    private val data = mutableListOf<NotificationItem>(
        NotificationItem(title = "The Best Title" , details = "Culpa cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo", date = "April 30, 2014 1:01 PM"),
        NotificationItem(title = "SUMMER OFFER 98% Cashback" , details = "Culpa cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor", date = "April 30, 2014 1:01 PM"),
        NotificationItem(title = "Special Offer 25% OFF" , details = "Culpa cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo", date = "April 30, 2014 1:01 PM")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotficationOfferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        adapter = NotificationsRecyclerAdapter(data,isOffer = true);
        binding.offersRv.adapter = adapter
    }

}