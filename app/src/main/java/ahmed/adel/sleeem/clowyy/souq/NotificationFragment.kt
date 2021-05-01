package ahmed.adel.sleeem.clowyy.souq

import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentHomeBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentNotificationBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class NotificationFragment : Fragment() {


    lateinit var binding:FragmentNotificationBinding
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
    }
}