package kr.co.lee.part7_20

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.IBinder

class PlayService : Service(), MediaPlayer.OnCompletionListener {
    var player: MediaPlayer? = null
    lateinit var filePath: String

    // Activity에서 실행시키는 리시버
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // 리시버를 통해 넘어온 Intent에서 데이터 얻오이기
            val mode = intent?.getStringExtra("mode")
            if(mode != null) {
                // 시작 모드라면
                if(mode == "start") {
                    try {
                        // player가 null이 아니고 진행중이라면
                        if(player != null && player?.isPlaying!!) {
                            // stop, release
                            player?.stop()
                            player?.release()
                            player = null
                        }

                        // 음악 play를 위한 MediaPlayer 준비
                        player = MediaPlayer()
                        player?.setDataSource(filePath)
                        player?.prepare()
                        player?.start()
                        // Activity에 duration 데이터 전달
                        // 암시적 인텐트로 리시버에 데이터넣고 sendBroadcast를 통해 인텐트를 발생시켜 리시버 실행
                        val aIntent = Intent("com.example.PLAY_TO_ACTIVITY")
                        aIntent.putExtra("mode", "start")
                        aIntent.putExtra("duration", player?.duration)
                        sendBroadcast(aIntent)
                    } catch(e: Exception) {
                        e.printStackTrace()
                    }
                } else if(mode == "stop") {
                    if(player != null && player?.isPlaying!!) {
                        // MediaPlayer 멈추고 리소스 해제
                        player?.stop()
                        player?.release()
                        player = null
                    }
                }
            }
        }
    }

    // Called when the end of a media source is reached during playback
    override fun onCompletion(mp: MediaPlayer?) {
        // 음악 play가 끝나면 activity에 알려준다
        val intent = Intent("com.example.PLAY_TO_ACTIVITY")
        intent.putExtra("mode", "stop")
        sendBroadcast(intent)
        // Service 자신을 종료시킨다
        stopSelf()
    }

    override fun onCreate() {
        super.onCreate()
        // 리시버 등록
        registerReceiver(receiver, IntentFilter("com.example.PLAY_TO_SERVICE"))
    }

    override fun onDestroy() {
        super.onDestroy()
        // 리시버 해제
        unregisterReceiver(receiver)
    }

    // Context.startService를 호출하여 서비스를 시작할 때마다 시스템에서 호출됩니다
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        filePath = intent?.getStringExtra("filePath")!!
        // 이미 서비스가 이전에 구동되어 음악이 플레이 되고 있는 상황
        if(player != null) {
            // 액티비티에게 리시버를 이용해 데이터 전달
            val aIntent = Intent("com.example.PLAY_TO_SERVICE")
            aIntent.putExtra("mode", "restart")
            aIntent.putExtra("duration", player?.duration)
            aIntent.putExtra("current", player?.currentPosition)
            sendBroadcast(aIntent)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }
}