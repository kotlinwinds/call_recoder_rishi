package com.ele.db.dao


import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.ele.db.Entities.NotificationEntity


@Dao
interface NotificationDao {

    @get:Query("Select * From NotificationEntity")
    val generalNotifications: List<NotificationEntity>

    @get:Query("Select * From NotificationEntity WHERE type='Incoming' ")
    val getIncomingCall: List<NotificationEntity>

    @get:Query("Select * From NotificationEntity WHERE type='Outgoing' ")
    val getOutingCall: List<NotificationEntity>

    @Insert
    fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE NotificationEntity SET read =1 WHERE read=0")
    fun updateAllRead()


    @Query("UPDATE NotificationEntity SET read=1 where notificationId= :primarykey")
    fun updateSingleRead(primarykey: Int)


    @Query("DELETE FROM NotificationEntity")
    fun deleteAll()


}
