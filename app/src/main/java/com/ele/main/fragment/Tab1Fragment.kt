package com.ele.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ele.R
import com.ele.all.AllAdapter
import com.ele.model.LanguageModel
import kotlinx.android.synthetic.main.fragment_one.view.*
import java.util.*


class Tab1Fragment : Fragment() {
    private lateinit var mAllAdapter: AllAdapter
    private var list = getLanData()
    private lateinit var mActivity: AppCompatActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=(activity!! as AppCompatActivity)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_one, container, false)
        initView(v)
        return v
    }

    private fun initView(v: View?) {

            v!!.recyclerViewAll.layoutManager = LinearLayoutManager(mActivity)
            mAllAdapter = AllAdapter(list,object : AllAdapter.ItemClickListener {
                override fun onItemClicked(repos: LanguageModel) {
                   // toast(repos.langNameOwn)
                }

            })
            v.recyclerViewAll.adapter = mAllAdapter

        }



    private fun getLanData(): ArrayList<LanguageModel> {
        val listData = ArrayList<LanguageModel>()
        listData.add(LanguageModel("English", "English", "en", 0))
        listData.add(LanguageModel("Hindi", "हिंदी", "hi", 1))
        listData.add(LanguageModel("Telugu", "తెలుగు", "te", 2))
        listData.add(LanguageModel("Tamil", "தமிழ்", "ta", 3))
        listData.add(LanguageModel("Malayalam", "മലയാളം", "ml", 4))
        listData.add(LanguageModel("Gujarati", "ગુજરાતી", "gu", 5))
        listData.add(LanguageModel("Assamese ", "অসমীয়া", "as", 6))
        listData.add(LanguageModel("Bengali", "বাঙালি", "bn", 7))
        listData.add(LanguageModel("Kannada", "ಕನ್ನಡ", "kn", 8))
        listData.add(LanguageModel("Marathi", "मराठी", "mr", 9))
        listData.add(LanguageModel("Oriya", "ଓଡ଼ିଆ", "or", 10))

        return listData
    }
}