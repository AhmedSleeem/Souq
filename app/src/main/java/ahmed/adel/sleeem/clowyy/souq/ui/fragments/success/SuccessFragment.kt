package ahmed.adel.sleeem.clowyy.souq.ui.fragments.success

import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSuccessBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class SuccessFragment : Fragment() ,View.OnClickListener{
    private var _binding: FragmentSuccessBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onClick(v: View?) {

    }

}