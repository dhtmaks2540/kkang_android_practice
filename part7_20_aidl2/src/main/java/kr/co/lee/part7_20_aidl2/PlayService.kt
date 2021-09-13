package kr.co.lee.part7_20_aidl2

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.RemoteException

class PlayService: Service() {
    val MEDIA_STATUS_STOP = 0
    val MEDIA_STATUS_RUNNING = 1
    val MEDIA_STATUS_COMPLETED = 2

    var status = MEDIA_STATUS_STOP

    var player: MediaPlayer? = null
    var filePath: String? = null

    override fun onDestroy() {
        player!!.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        filePath = intent.getStringExtra("filePath")
        return object : IPlayService.Stub() {
            override fun currentPosition(): Int {
                return if (player!!.isPlaying) {
                    player!!.currentPosition
                } else {
                    0
                }
            }

            override fun getMaxDuration(): Int {
                return if (player!!.isPlaying) {
                    player!!.duration
                } else {
                    0
                }
            }

            override fun start() {
                player = MediaPlayer()
                try {
                    player!!.setDataSource(filePath)
                    player!!.prepare()
                    player!!.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                player!!.setOnCompletionListener { status = MEDIA_STATUS_COMPLETED }
                status = MEDIA_STATUS_RUNNING
            }

            override fun stop() {
                if (player!!.isPlaying) {
                    player!!.stop()
                }
                status = MEDIA_STATUS_STOP
            }

            override fun getMediaStatus(): Int {
                return status
            }
        }
    }
}