package com.ele.db.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class NotificationEntity {

    @PrimaryKey(autoGenerate = true)
    var notificationId: Int = 0
    var read = 0
    var server_time:String?=null
    var name: String?=null
    var number: String?=null
    var type: String?=null
    var filePath: String?=null

}
