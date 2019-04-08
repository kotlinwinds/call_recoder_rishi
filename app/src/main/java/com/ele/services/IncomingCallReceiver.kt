package com.ele.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import com.android.internal.telephony.ITelephony
import com.ele.push_nofi.FirebaseIntentService
import java.util.*

class IncomingCallReceiver : BroadcastReceiver() {
    lateinit var serviceIntent: Intent
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        serviceIntent = Intent(context, FirebaseIntentService::class.java)
        val telephonyService: ITelephony
        try {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            var number = Objects.requireNonNull<Bundle>(intent.extras).getString(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state != null) {
                if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                    if (number == null)
                        number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    Log.d("TAGS", "PhoneReceiver INCOMING_NUMBER phone number 2: $number")

                    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    try {
                        @SuppressLint("PrivateApi")
                        val m = tm.javaClass.getDeclaredMethod("answerRingingCall")
                        m.isAccessible = true
                        telephonyService = m.invoke(tm) as ITelephony
                        if (number != null) {
                            telephonyService.endCall()
                            Log.d("TAGS", "Answered incoming Ending the call from 1: :   $number! ")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            } else if (number != null) {
                Log.d("TAGS", "outgoing call phone number 1: $number")
            }


            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK, ignoreCase = true)) {
                serviceIntent.putExtra("name", getContactName(context, number!!))
                serviceIntent.putExtra("number", number)
                serviceIntent.putExtra("type", "Incoming")
                Log.d("TAGS", "Answered Incoming call Answered Accept 33333 $number ")
                context.startService(serviceIntent)
            }else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK, ignoreCase = true)) {
                serviceIntent.putExtra("name", getContactName(context, number!!))
                serviceIntent.putExtra("number", number)
                serviceIntent.putExtra("type", "Incoming")
                Log.d("TAGS", "Answered Incoming call Answered Accept 11111 $number ")
                context.startService(serviceIntent)
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE, ignoreCase = true)) {
                context.stopService(serviceIntent)
                Log.d("TAGS", "Answered  call Idle End:   $number! ")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("ObsoleteSdkInt")
    fun getContactName(con: Context, phoneNumber: String): String {
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
 