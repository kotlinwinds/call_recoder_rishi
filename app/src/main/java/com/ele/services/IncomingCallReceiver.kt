package com.ele.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import java.lang.reflect.Method
import java.util.Objects

import com.android.internal.telephony.ITelephony

class IncomingCallReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {

        val telephonyService: ITelephony
        try {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = Objects.requireNonNull<Bundle>(intent.extras).getString(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING, ignoreCase = true)) {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                try {
                    @SuppressLint("PrivateApi")
                    val m = tm.javaClass.getDeclaredMethod("answerRingingCall")

                    m.isAccessible = true
                    telephonyService = m.invoke(tm) as ITelephony

                    if (number != null) {
                        telephonyService.endCall()
                        Log.d("TAGS","Answered incoming Ending the call from: :   $number! ")
                        Toast.makeText(context, "Ending the call from: $number", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Log.d("TAGS","Answered incoming call Ring :   $number! ")
                Toast.makeText(context, "Ring " + number!!, Toast.LENGTH_SHORT).show()

            }
            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK, ignoreCase = true)) {
                Log.d("TAGS","Answered incoming call Answered Accept ")
                Toast.makeText(context, "Answered " + number!!, Toast.LENGTH_SHORT).show()
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE, ignoreCase = true)) {
                Log.d("TAGS","Answered incoming call Idle end :   $number! ")
                Toast.makeText(context, "Idle " + number!!, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
 