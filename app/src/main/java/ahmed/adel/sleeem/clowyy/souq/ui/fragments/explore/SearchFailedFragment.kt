package ahmed.adel.sleeem.clowyy.souq.ui.explore_fragment

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSearchFailedBinding
import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentSearchSucceedBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

class SearchFailedFragment : Fragment() {


    private lateinit var binding: FragmentSearchFailedBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFailedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCategory.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchFailedFragment_to_listCategoryFragment);
        }

        binding.filterIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchFailedFragment_to_filterFragment);
        }

        binding.shortIv.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchFailedFragment_to_shortByFragment);
        }
    }

}