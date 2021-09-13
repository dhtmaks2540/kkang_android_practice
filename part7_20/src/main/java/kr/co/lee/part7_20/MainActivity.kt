package kr.co.lee.part7_20

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var playBtn: ImageView
    lateinit var stopBtn: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var titleView: TextView

    lateinit var filePath: String
    var runThread: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playBtn = findViewById(R.id.lab1_play)
        stopBtn = findViewById(R.id.lab1_stop)
        progressBar = findViewById(R.id.lab1_progress)
        titleView = findViewById(R.id.lab1_title)

        playBtn.setOnClickListener(this)
        stopBtn.setOnClickListener(this)

        titleView.text = "music.mp3"
        stopBtn.isEnabled = false

        // 외부 경로 얻어오기
        filePath = Environment.getExternalStorageDirectory().absolutePath+"/Music/music.mp3"

        // 권한 등록이 안되어 있다면 다이얼로그를 통한 권한 요청
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
        }
        
        // Activity의 Receiver를 시스템에 등록
        registerReceiver(receiver, IntentFilter("com.example.PLAY_TO_ACTIVITY"))

        // Service 인텐트 실행
        val intent = Intent(this, PlayService::class.java)
        // 인텐트에 파일경로 주기
        intent.putExtra("filePath", filePath)
        // 서비스 실행
        startService(intent)
    }

    inner class ProgressThread: Thread() {
        override fun run() {
            while(runThread) {
                // runThread가 true라면 1초마다 progressbar 1씩 증가
                progressBar.incrementProgressBy(1000)
                SystemClock.sleep(1000)
                // 프로그레스바가 가득차면 thread 종료
                if(progressBar.progress == progressBar.max) {
                    runThread = false
                }
            }
        }
    }

    // 서비스로부터 데이터를 받기 위한 리시버
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val mode = intent?.getStringExtra("mode")
            if(mode != null) {
                // 최초 서비스가 구동된 상태라면 duration 설정만 획득
                if(mode == "start") {
                    val duration = intent?.getIntExtra("duration", 0)
                    // 프로그래스바 최대값을 duration으로
                    progressBar.max = duration!!
                    progressBar.progress = 0
                } else if(mode == "stop") { // 서비스쪽에서 음악 플레이가 종료된 상황이라면
                    runThread = false // thread 종료
                } else if(mode == "restart") { // 액티비티가 다시 시작되었다면
                    // 서비스쪽에서 음악을 play하고 있는 도중일 수 있어서
                    // duration과 current 값 얻어오기
                    val duration = intent.getIntExtra("duration", 0)
                    val current = intent.getIntExtra("current", 0)
                    // 프로그래스바 조정
                    progressBar.max = duration
                    progressBar.progress = current
                    runThread = true
                    // ProgressBar를 증가시키는 Thread
                    val thread = ProgressThread()
                    thread.start()

                    playBtn.isEnabled = false
                    stopBtn.isEnabled = true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Activity가 종료되면서 리시버 등록 해제
        unregisterReceiver(receiver)
    }

    override fun onClick(v: View?) {
        when(v) {
            // 시작 버튼이라면 서비스쪽 리시버 실행
            playBtn -> {
                val intent = Intent("com.example.PLAY_TO_SERVICE")
                intent.putExtra("mode", "start")
                sendBroadcast(intent)

                runThread = true
                val thread = ProgressThread()
                thread.start()
                playBtn.isEnabled = false
                stopBtn.isEnabled = true
            }
            // 멈춤 버튼 이라면 서비스쪽 리시버 실행
            stopBtn -> {
                val intent = Intent("com.example.PLAY_TO_SERVICE")
                intent.putExtra("mode", "stop")
                sendBroadcast(intent)
                runThread = false
                progressBar.progress = 0
                playBtn.isEnabled = true
                stopBtn.isEnabled = false
            }
        }
    }
}