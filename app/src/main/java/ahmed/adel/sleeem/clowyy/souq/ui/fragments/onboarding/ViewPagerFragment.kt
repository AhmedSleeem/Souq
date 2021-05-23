package ahmed.adel.sleeem.clowyy.souq.ui.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.onboarding.adapter.ViewPagerAdapter
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.onboarding.screens.FirstScreen
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.onboarding.screens.SecondScreen
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.onboarding.screens.ThirdScreen
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class ViewPagerFragment : Fragment() {

    private lateinit var binding: ViewPagerFragment



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        val wormDotsIndicator = view.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter
        wormDotsIndicator.setViewPager2(viewPager)

        return view    }


}