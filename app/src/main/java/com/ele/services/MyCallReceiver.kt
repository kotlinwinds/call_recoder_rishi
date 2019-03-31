package com.ele.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log


class MyCallReceiver : BroadcastReceiver() {

    companion object {
        var manager: TelephonyManager? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_NEW_OUTGOING_CALL == intent.action) {
           val s=intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
           Log.d("TAGS","OUT GOING call:  $s")
        }

        // Start Listening to the call....
        if (null == manager) {
            manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
    }
}


