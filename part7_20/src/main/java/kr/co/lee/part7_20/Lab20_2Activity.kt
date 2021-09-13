package kr.co.lee.part7_20

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.co.lee.part7_20_aidl2.IPlayService

class Lab20_2Activity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val MEDIA_STATUS_STOP = 0
        const val MEDIA_STATUS_RUNNING = 1
        const val MEDIA_STATUS_COMPLETED = 2
    }

    lateinit var playBtn: ImageView
    lateinit var stopBtn: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var titleView: TextView

    lateinit var filePath: String
    var runThread: Boolean = false

    var pService: IPlayService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab20_2)

        playBtn = findViewById(R.id.lab2_play)
        stopBtn = findViewById(R.id.lab2_stop)
        progressBar = findViewById(R.id.lab2_progress)
        titleView = findViewById(R.id.lab2_title)

        playBtn.setOnClickListener(this)
        stopBtn.setOnClickListener(this)

        titleView.text = "music.mp3"
        stopBtn.isEnabled = false
        playBtn.isEnabled = false

        filePath = Environment.getExternalStorageDirectory().absolutePath + "/Music/music.mp3"

        val intent = Intent("kr.co.lee.part7_20.ACTION_PLAY")
        intent.setPackage("kr.co.lee.part7_20_aidl2")
        intent.putExtra("filePath", filePath)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    val connection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            pService = IPlayService.Stub.asInterface(service)
            playBtn.isEnabled = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            pService = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
        runThread = false
    }

    override fun onClick(v: View?) {
        if (v === playBtn) {
            if (pService != null) {
                try {
                    pService?.start()
                    progressBar.max = pService?.maxDuration!!
                    runThread = true
                    val thread= ProgressThread()
                    thread.start()
                    playBtn.isEnabled = false
                    stopBtn.isEnabled = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else if (v === stopBtn) {
            if (pService != null) {
                try {
                    pService?.stop()
                    runThread = false
                    progressBar.progress = 0
                    playBtn.isEnabled = true
                    stopBtn.isEnabled = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    inner class ProgressThread : Thread() {
        override fun run() {
            while (runThread) {
                progressBar.incrementProgressBy(1000)
                SystemClock.sleep(1000)
                if (progressBar.progress == progressBar.max) {
                    runThread = false
                }
            }
        }
    }
}