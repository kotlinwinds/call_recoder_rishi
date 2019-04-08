package com.ele.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ele.R
import com.ele.all.AllAdapter
import com.ele.db.Entities.NotificationEntity
import com.ele.db.database.NotificationRoomDatabase
import com.ele.utils.onCall
import kotlinx.android.synthetic.main.fragment_one.view.*


class Tab3Fragment : Fragment() {
    private lateinit var mAllAdapter: AllAdapter
    private lateinit var mActivity: AppCompatActivity
    private lateinit var recyclerViewAll: RecyclerView
    private var list: MutableList<NotificationEntity> = mutableListOf()
    private val db by lazy { NotificationRoomDatabase.getDatabase(mActivity) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as AppCompatActivity)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_one, container, false)
        initView(v)
        return v
    }

    private fun initView(v: View?) {
        recyclerViewAll=v!!.recyclerViewAll
        recyclerViewAll.layoutManager = LinearLayoutManager(mActivity)
    }

    override fun onStart() {
        super.onStart()
        reload()
    }

    private fun reload(){
        list = db.notificationDao().getOutingCall as MutableList<NotificationEntity>
        list.reverse()
        mAllAdapter = AllAdapter(list, object : AllAdapter.ItemClickListener {
            override fun onItemClicked(repos: NotificationEntity) {
                try {
                    mActivity.onCall(repos.number!!)
                } catch (e: Exception) {
                }
            }

        })
        recyclerViewAll.adapter = mAllAdapter
        mAllAdapter.notifyDataSetChanged()
    }

}