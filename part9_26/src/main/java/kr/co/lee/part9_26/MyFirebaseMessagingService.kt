package kr.co.lee.part9_26

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // 서버에서 넘어온 데이터
        val from = remoteMessage.from
        val data = remoteMessage.data
        val msg = data["msg"]

        // 알림을 사용하기 위해 NotificationManager 얻어오기
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        var builder: NotificationCompat.Builder? = null

        // NotificationChannel을 사용하기 위해 버전 분기
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // channel 설정
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channelDescription = "My Channel One Description"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = channelDescription

            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            builder = NotificationCompat.Builder(this)
        }

        // 알림 설정
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setContentTitle("Server Message")
        builder.setWhen(System.currentTimeMillis())
        builder.setContentText(msg)
        builder.setAutoCancel(true)

        manager.notify(222, builder.build())
    }
}