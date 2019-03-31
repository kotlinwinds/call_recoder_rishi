package com.ele.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.ele.R
import com.ele.main.fragment.Tab1Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter: MyAdapter? = null


    private val tabIcons = intArrayOf(
        R.drawable.ic_call,
        R.drawable.ic_call_outgoing,
        R.drawable.ic_call_received
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        adapter = MyAdapter(supportFragmentManager, this)
        adapter!!.addFragment(Tab1Fragment(), resources.getString(R.string.all) ,  tabIcons[0])
        adapter!!.addFragment(Tab1Fragment(),  resources.getString(R.string.incoming) ,  tabIcons[1])
        adapter!!.addFragment(Tab1Fragment(),  resources.getString(R.string.outgoing) ,  tabIcons[2])

        viewPager!!.adapter = adapter
        tabLayout!!.setupWithViewPager(viewPager)
        highLightCurrentTab(0)
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                highLightCurrentTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun highLightCurrentTab(position: Int) {
        for (i in 0 until tabLayout!!.tabCount) {
            val tab = tabLayout!!.getTabAt(i)!!
            tab.customView = null
            tab.customView = adapter!!.getTabView(i)
        }
        val tab = tabLayout!!.getTabAt(position)!!
        tab.customView = null
        tab.customView = adapter!!.getSelectedTabView(position)
    }
}