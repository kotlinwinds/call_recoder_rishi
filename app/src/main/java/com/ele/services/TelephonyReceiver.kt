package com.ele.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.TelephonyManager
import android.util.Log
import android.view.KeyEvent


class TelephonyReceiver : BroadcastReceiver() {

    override fun onReceive(arg0: Context, intent: Intent?) {

        if (intent!!.action != "android.intent.action.PHONE_STATE")
            return

        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == TelephonyManager.EXTRA_STATE_RINGING) {
            val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            val buttonDown = Intent(Intent.ACTION_MEDIA_BUTTON)
            buttonDown.putExtra(Intent.EXTRA_KEY_EVENT, KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK))
            arg0.sendOrderedBroadcast(buttonDown, "android.permission.CALL_PRIVILEGED")

            Log.d("TAGS","Answered incoming call from: $number")


            val tm =  arg0.getSystemService(Context.TELEPHONY_SERVICE)
            try {
                if (tm == null) {
                    throw  NullPointerException("tm == null");
                }
                Log.d("TAGS","Answered incoming call answerRingingCall : ${tm.javaClass.getMethod("call").invoke(tm)}")
            } catch (e:Exception) {
                Log.e("sdsd", "Unable to use the Telephony Manager directly.", e);
            }
        }



        return






        /*   try {
            if (intent.action == "android.intent.action.NEW_OUTGOING_CALL") {
                //Toast.makeText(context, "Outgoign call", 1000).show();
                val number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
                Log.d("TAGS","Telephone Outgoing Number:  $number")
                Log.d("TAGS","Telephone Outgoing Name:  ${getContactName(arg0, number!!)}")


            } else {
                //get the phone state
                val newPhoneState =
                    if (intent.hasExtra(TelephonyManager.EXTRA_STATE))
                        intent.getStringExtra(TelephonyManager.EXTRA_STATE) else null

                val bundle = intent.extras

                if (newPhoneState != null && newPhoneState == TelephonyManager.EXTRA_STATE_RINGING) {
                    //read the incoming call number
                    val phoneNumber = bundle?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    val type = bundle?.getString(TelephonyManager.EXTRA_STATE)
                    Log.d("PHONE RECEIVER", "Telephone is now ringing $phoneNumber")
                    Log.d("PHONE RECEIVER", "Telephone  Nanme ${getContactName(arg0, phoneNumber!!)}")

                    Log.d("TAGS","Telephone Incoming Number:  $phoneNumber")
                    Log.d("TAGS","Telephone Incoming Name:  ${getContactName(arg0, phoneNumber)}")

                } else if (newPhoneState != null && newPhoneState == TelephonyManager.EXTRA_STATE_IDLE) {
                    //Once the call ends, phone will become idle
                    Log.d("PHONE RECEIVER", "Telephone is now idle")
                } else if (newPhoneState != null && newPhoneState == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                    //Once you receive call, phone is busy
                    Log.d("PHONE RECEIVER", "Telephone is now busy")
                }

            }

        } catch (ee: Exception) {
            Log.i("Telephony receiver", ee.message)
        }*/

    }

    @SuppressLint("ObsoleteSdkInt")
    fun getContactName(cnt:Context,phoneNumber: String): String {
        var uri: Uri = Uri.parse("content://com.android.contacts/phone_lookup")
        val projection: Array<String> = arrayOf("display_name")
        uri = Uri.withAppendedPath(uri, Uri.encode(phoneNumber))
        val cursor = cnt.contentResolver.query(uri, projection, null, null, null)
        var contactName = "unknown"
        if (cursor!!.moveToFirst()) {
            contactName = cursor.getString(0)
        }
        cursor.close()
        return contactName
    }
}