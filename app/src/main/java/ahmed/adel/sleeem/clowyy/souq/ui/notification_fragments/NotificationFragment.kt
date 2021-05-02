package ahmed.adel.sleeem.clowyy.souq.ui.notification_fragments

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentNotificationBinding
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation

class NotificationFragment : Fragment() {


    lateinit var binding: FragmentNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBar.setNavigationIcon(R.drawable.ic_arrow_back)

        binding.appBar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
            binding.notificationOfferTv.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_notificationFragment_to_notficationOfferFragment);
            }

            binding.notificationFeedTv.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_notificationFragment_to_notificationFeedFragment);
            }

            binding.notificationActivityTv.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_notificationFragment_to_notificationActivityFragment);
            }
    }
}