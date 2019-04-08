package com.ele.services

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ele.db.Entities.NotificationEntity
import com.ele.db.database.NotificationRoomDatabase
import com.ele.model.ModelData
import kotlinx.android.synthetic.main.fragment_two.*


class Main : AppCompatActivity() {

    private var list:MutableList<NotificationEntity> = mutableListOf()
    private val db by lazy { NotificationRoomDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ele.R.layout.fragment_two)


        tab2.setOnClickListener {

            list= db.notificationDao().generalNotifications as MutableList<NotificationEntity>
            for(i:NotificationEntity in list){
                Log.d("TAGS", " ${i.number} ${i.name}   ${i.type}  ${i.server_time} ")
            }

        }
    }
}