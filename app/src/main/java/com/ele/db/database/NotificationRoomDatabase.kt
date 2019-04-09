package com.ele.db.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ele.db.Entities.NotificationEntity
import com.ele.db.dao.NotificationDao


@Database(entities = arrayOf(NotificationEntity::class), version = 1)
abstract class NotificationRoomDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
    companion object {
        private var INSTANCE: NotificationRoomDatabase? = null
        internal fun getDatabase(context: Context): NotificationRoomDatabase {
            if (INSTANCE == null) {
                synchronized(NotificationRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, NotificationRoomDatabase::class.java, "edel").allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}