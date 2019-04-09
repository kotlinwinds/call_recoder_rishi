package com.ele.push_nofi

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.ele.db.Entities.NotificationEntity
import com.ele.db.database.NotificationRoomDatabase
import java.text.SimpleDateFormat
import java.util.*


class FirebaseIntentService : IntentService("FirebaseIntent") {

    private val db by lazy { NotificationRoomDatabase.getDatabase(this) }
    val TAG = "FirebaseTag"
    private val notification = NotificationEntity()


    @SuppressLint("SimpleDateFormat")
    override fun onHandleIntent(intent: Intent?) {
        try {
            if (intent!!.getStringExtra("name") != null) {
                notification.name = intent.getStringExtra("name")
            }
            if (intent.getStringExtra("number") != null) {
                notification.number = intent.getStringExtra("number")
            }
            if (intent.getStringExtra("type") != null) {
                notification.type = intent.getStringExtra("type")
            }

            if (intent.getStringExtra("filePath") != null) {
                notification.filePath = intent.getStringExtra("filePath")
            }
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentDate = sdf.format(c.time)
            try {
                notification.server_time = currentDate
            } catch (e: Exception) {
            }
            db.notificationDao().insertNotification(notification)
            Log.e(TAG, "FINISHED ADDING DATA TO DB")


        } catch (e: Exception) {
        }

    }

}

