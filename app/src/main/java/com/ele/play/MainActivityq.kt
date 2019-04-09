package com.ele.play

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import com.ele.R

class MainActivityq : AppCompatActivity() {
    private var mediaPlayer = MediaPlayer()
    internal lateinit var seekBarProgress: SeekBar
    internal lateinit var buttonPlayPause: ImageButton
    private var mediaFileLengthInMilliseconds: Int = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dialog = Dialog(this@MainActivityq)
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

                val descriptor = assets.openFd("aa.mp3")
                mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                descriptor.close()

                mediaPlayer.prepare()
                mediaPlayer.setVolume(1f, 1f)
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