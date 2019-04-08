package com.ele.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.TelephonyManager
import android.util.Log
import com.android.internal.telephony.ITelephony
import com.ele.push_nofi.FirebaseIntentService


class MyPhoneReciever : BroadcastReceiver() {
    lateinit var serviceIntent: Intent
    private var phoneNumber: String? = null
    override fun onReceive(context: Context, intent: Intent) {
        serviceIntent = Intent(context, FirebaseIntentService::class.java)
        phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        val telephonyService: ITelephony
        if (phoneNumber == null) {
            when {
                intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    Log.d("TAGS", "call Accept  ")
                }
                intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_IDLE -> {
                    Log.d("TAGS", "CALL END ")
                    context.stopService(serviceIntent)
                }
                intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_RINGING -> {
                    if (phoneNumber == null)
                        phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                    serviceIntent.putExtra("name", getContactName(context, phoneNumber!!))
                    serviceIntent.putExtra("number", phoneNumber)
                    serviceIntent.putExtra("type", "Incoming")
                    context.startService(serviceIntent)
                    Log.d("TAGS", "Incoming call accept: $phoneNumber")

                }
            }
        } else {
            serviceIntent.putExtra("name", getContactName(context, phoneNumber!!))
            serviceIntent.putExtra("number", phoneNumber)
            serviceIntent.putExtra("type", "Outgoing")
            context.startService(serviceIntent)
            Log.d("TAGS", "Outgoing call : $phoneNumber")

        }

    }

    private fun getContactName(con: Context, phoneNumber: String): String {
        var uri: Uri = Uri.parse("content://com.android.contacts/phone_lookup")
        val projection: Array<String> = arrayOf("display_name")
        uri = Uri.withAppendedPath(uri, Uri.encode(phoneNumber))
        val cursor = con.contentResolver.query(uri, projection, null, null, null)
        var contactName = "unknown"
        if (cursor!!.moveToFirst()) {
            contactName = cursor.getString(0)
        }
        cursor.close()
        return contactName
    }
}