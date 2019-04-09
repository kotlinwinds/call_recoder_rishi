package com.ele.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.telephony.TelephonyManager
import android.util.Log
import com.ele.push_nofi.FirebaseIntentService
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MyPhoneReciever : BroadcastReceiver() {
    lateinit var serviceIntent: Intent
    private var phoneNumber: String? = null
    private lateinit var mr: MediaRecorder
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        mr = MediaRecorder()
        serviceIntent = Intent(context, FirebaseIntentService::class.java)
        phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        if (phoneNumber == null) {
            when {
                intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    Log.d("TAGS", "call Accept  ")
                }
                intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_IDLE -> {
                    Log.d("TAGS", "CALL END ")
                    try {
                        stopRecording()
                    } catch (e: Exception) {
                    }
                    context.stopService(serviceIntent)
                }
                intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_RINGING -> {
                    if (phoneNumber == null)
                        phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    try {
                        startRecording()
                    } catch (e: Exception) {
                    }
                    serviceIntent.putExtra("name", getContactName(context, phoneNumber!!))
                    serviceIntent.putExtra("number", phoneNumber)
                    serviceIntent.putExtra("type", "Incoming")
                    serviceIntent.putExtra("filePath", getFilename())
                    context.startService(serviceIntent)
                    Log.d("TAGS", "Incoming call accept: $phoneNumber")

                }
            }
        } else {
            startRecording()
            serviceIntent.putExtra("name", getContactName(context, phoneNumber!!))
            serviceIntent.putExtra("number", phoneNumber)
            serviceIntent.putExtra("type", "Outgoing")
            serviceIntent.putExtra("filePath", getFilename())
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

    private fun startRecording() {
        mr.setAudioSource(MediaRecorder.AudioSource.MIC)
        mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mr.setOutputFile(getFilename())

        try {
            mr.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mr.start()
        Log.d("TAGS"," Call recoder........")
    }

    private fun stopRecording() {
        try {
            mr.stop()
            mr.release()
        } catch (e: Exception) {
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFilename(): String {
        val df = SimpleDateFormat("DD-MM-yyyy,HH:mm:ss")
        val date = df.format(Calendar.getInstance().time)
        val filepath = Environment.getExternalStorageDirectory().path
        val file = File(filepath, "AudioRecorderWinds")

        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + "/"+"winds_"+date+".amr"
    }
}