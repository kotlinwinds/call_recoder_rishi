package com.ele.services

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ele.R.layout.activity_main)

       // val name = getContactName("89208285853")
       // Log.d("PHONE RECEIVER", "Telephone $name")



    }


    @SuppressLint("ObsoleteSdkInt")
    fun getContactName(phoneNumber: String): String {
        var uri: Uri = Uri.parse("content://com.android.contacts/phone_lookup")
        val projection: Array<String> = arrayOf("display_name")
        uri = Uri.withAppendedPath(uri, Uri.encode(phoneNumber))
        val cursor = this.contentResolver.query(uri, projection, null, null, null)
        var contactName = "unknown"
        if (cursor!!.moveToFirst()) {
            contactName = cursor.getString(0)
        }
        cursor.close()
        return contactName
    }
}