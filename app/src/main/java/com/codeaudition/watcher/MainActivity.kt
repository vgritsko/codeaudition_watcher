package com.codeaudition.watcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.codeaudition.watcher.databinding.ActivityMainBinding
import com.codeaudition.watcher.ui.map.LocationFragment
import com.codeaudition.watcher.ui.timeline.TimelineFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val MAX_NUM_PAGES = 2

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout:TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        viewPager.adapter = PagerAdapter(this)

        tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0-> tab.text = "Map"
                1-> tab.text = "TimeLine"
            }
        }.attach()
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = MAX_NUM_PAGES

        override fun createFragment(position: Int): Fragment =
                when (position) {
                    0 -> LocationFragment()
                    1 -> TimelineFragment()
                    else -> LocationFragment()
                }

    }

}