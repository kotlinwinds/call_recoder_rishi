package com.ele.main.fragment

import android.app.Dialog
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import com.ele.R
import com.ele.all.AllAdapter
import com.ele.db.Entities.NotificationEntity
import com.ele.db.database.NotificationRoomDatabase
import kotlinx.android.synthetic.main.fragment_one.view.*


class Tab1Fragment : Fragment() {
    private lateinit var mAllAdapter: AllAdapter
    private lateinit var mActivity: AppCompatActivity
    private lateinit var recyclerViewAll: RecyclerView
    private var list: MutableList<NotificationEntity> = mutableListOf()
    private val db by lazy { NotificationRoomDatabase.getDatabase(mActivity) }

    private var mediaPlayer = MediaPlayer()
    internal lateinit var seekBarProgress: SeekBar
    internal lateinit var buttonPlayPause: ImageButton
    private var mediaFileLengthInMilliseconds: Int = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as AppCompatActivity)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_one, container, false)
        initView(v)
        return v
    }

    private fun initView(v: View?) {
        recyclerViewAll=v!!.recyclerViewAll
        recyclerViewAll.layoutManager = LinearLayoutManager(mActivity)
    }

    override fun onStart() {
        super.onStart()
        reload()
    }

    private fun reload(){
        list = db.notificationDao().generalNotifications as MutableList<NotificationEntity>
        list.reverse()
        for(m:NotificationEntity in list){
            Log.d("TAGS","FilePath : ${m.number}  , ${m.name}  , ${m.type}  , ${m.filePath}  , ${m.server_time}  ,  ")
        }
        mAllAdapter = AllAdapter(list, object : AllAdapter.ItemClickListener {
            override fun onItemClicked(repos: NotificationEntity) {
                try {
                   val mediaPlayer = MediaPlayer()
                    val myUri = Uri.parse(repos.filePath)
                    mediaPlayer.setDataSource(mActivity,myUri)
                    mediaPlayer.prepare()
                    mediaPlayer.isLooping = true
                    mediaPlayer.start()
                 //   play(repos.filePath)
                    //mActivity.onCall(repos.number!!)
                } catch (e: Exception) {
                }
            }

        })
        recyclerViewAll.adapter = mAllAdapter
        mAllAdapter.notifyDataSetChanged()
    }

    fun play(filePath: String?) {
        mediaPlayer = MediaPlayer()
        val dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.popup_ringtones)
        dialog.setTitle("Title...")

         buttonPlayPause = dialog.findViewById<View>(R.id.play) as ImageButton
         seekBarProgress = dialog.findViewById<View>(R.id.SeekBarTestPlay) as SeekBar

        seekBarProgress.progress = 0
        seekBarProgress.max = 100
        dialog.setOnDismissListener { mediaPlayer.pause() }

        buttonPlayPause.setOnClickListener {
            try {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                    mediaPlayer = MediaPlayer()
                }
               //  /storage/emulated/0/AudioRecorderWinds/winds_1554840221004.amr
              //  val descriptor = mActivity.assets.openFd("aa.mp3")
               // mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
              //  descriptor.close()
                val myUri = Uri.parse(filePath)
                mediaPlayer.setDataSource(mActivity,myUri)
                mediaPlayer.prepare()
                mediaPlayer.isLooping = true
                mediaPlayer.start()
                primarySeekBarProgressUpdater()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            mediaFileLengthInMilliseconds = mediaPlayer.duration
        }

        dialog.show()
    }


    private fun primarySeekBarProgressUpdater() {
        seekBarProgress.progress =
            (mediaPlayer.currentPosition.toFloat() / mediaFileLengthInMilliseconds * 100).toInt() // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying) {
            val notification = Runnable { primarySeekBarProgressUpdater() }
            handler.postDelayed(notification, 1000)
        }
    }

}