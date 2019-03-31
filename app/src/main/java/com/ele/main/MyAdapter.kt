package com.ele.main

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.ele.R
import kotlinx.android.synthetic.main.custom_tab.view.*
import java.util.*

class MyAdapter internal constructor(fm: FragmentManager, private val context: Context) :
    FragmentStatePagerAdapter(fm) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    private val mFragmentIconList = ArrayList<Int>()
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String, tabIcon: Int) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
        mFragmentIconList.add(tabIcon)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        //return mFragmentTitleList.get(position);
        return null
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun getTabView(position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        val tabTextView = view.findViewById<TextView>(R.id.tabTextView)
        tabTextView.text = mFragmentTitleList[position]
        val tabImageView = view.tabImageView
        tabImageView.setImageResource(mFragmentIconList[position])
        return view
    }

    fun getSelectedTabView(position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        val tabTextView = view.findViewById<TextView>(R.id.tabTextView)
        tabTextView.text = mFragmentTitleList[position]
        tabTextView.setTextColor(ContextCompat.getColor(context, R.color.yellow))
        val tabImageView = view.tabImageView
        tabImageView.setImageResource(mFragmentIconList[position])
        tabImageView.setColorFilter(ContextCompat.getColor(context, R.color.yellow),PorterDuff.Mode.SRC_ATOP)
        return view
    }
}