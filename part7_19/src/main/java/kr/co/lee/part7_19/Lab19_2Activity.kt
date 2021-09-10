package kr.co.lee.part7_19

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat

class Lab19_2Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var basicBtn: Button
    lateinit var bigPictureBtn: Button
    lateinit var bigTextBtn: Button
    lateinit var inboxBtn: Button
    lateinit var progressBtn: Button
    lateinit var headsupBtn: Button
    lateinit var messageBtn: Button

    var manager: NotificationManager? = null
    var builder: NotificationCompat.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab19_2)

        basicBtn = findViewById(R.id.lab2_basic)
        bigPictureBtn = findViewById(R.id.lab2_bigpicture)
        bigTextBtn = findViewById(R.id.lab2_bigtext)
        inboxBtn = findViewById(R.id.lab2_inbox)
        progressBtn = findViewById(R.id.lab2_progress)
        headsupBtn = findViewById(R.id.lab2_headsup)
        messageBtn = findViewById(R.id.lab2_message)

        basicBtn.setOnClickListener(this)
        bigPictureBtn.setOnClickListener(this)
        bigTextBtn.setOnClickListener(this)
        inboxBtn.setOnClickListener(this)
        progressBtn.setOnClickListener(this)
        headsupBtn.setOnClickListener(this)
        messageBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // O(API 26) 이상부터는 NotificationChannel을 사용해야하기에 버전 분기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channelDescription = "My Channel One Description"

            var channel: NotificationChannel? = null
            if (v == headsupBtn) {
                // O 이상부터는 Heads up 방식으로 알림을 띄우기 위해서 NotificationChannel 생성자 정보에
                // NotificationManager.IMPORTANCE_HIGH로 설정되어있어야 한다
                channel =
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            } else {
                channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            }
            channel.description = channelDescription

            // NotificationChannel 생성
            manager?.createNotificationChannel(channel)
            // Channel을 가지고 Builder 만들기
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            // O 미만 버전은 NotificationChannel 사용 X
            builder = NotificationCompat.Builder(this)
        }

        // 알림 구성
        with(builder!!) {
            // 아이콘, 타이틀, 내용, 누르면 자동 사라짐
            setSmallIcon(android.R.drawable.ic_notification_overlay)
            setContentTitle("Content Title")
            setContentText("Content Message")
            setAutoCancel(true)
        }

        val intent = Intent(this, MainActivity::class.java)
        // 인텐트의 발생을 의뢰하기 위해 사용하는 클래스(액티비티)
        val pendingIntent =
            PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // 빌더의 setContentIntent 함수의 인자로 pendingIntent를 넣으면 알림을 눌뤘을 때 그 인텐트로 이동
        builder?.setContentIntent(pendingIntent)

        // 인텐트의 발생을 의뢰(BroadcastReceiver)
        val pIntent1 = PendingIntent.getBroadcast(
            this,
            0,
            Intent(this, NotiReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        // 알림에 액션 추가(브로드캐스트 리시버 사용)
        builder?.addAction(
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_menu_share,
                "ACTION1",
                pIntent1
            ).build()
        )

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.noti_large)
        // 아이콘 설정
        builder?.setLargeIcon(largeIcon)

        /*when (v) {
            bigPictureBtn -> {
                // 알림에 이미지 적용하기
                val bigPicture = BitmapFactory.decodeResource(resources, R.drawable.noti_big)
                val bigStyle = NotificationCompat.BigPictureStyle(builder)
                bigStyle.bigPicture(bigPicture)
                builder?.setStyle(bigStyle)
            }
            bigTextBtn -> {
                // 알림에 긴 문장 적용
                val bigTextStyle = NotificationCompat.BigTextStyle(builder)
                bigTextStyle.setSummaryText("BigText Summary")
                bigTextStyle.bigText("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라만세!!")
                builder?.setStyle(bigTextStyle)
            }
            inboxBtn -> {
                // 알림에 여러 데이터 목록 적용
                val style = NotificationCompat.InboxStyle(builder)
                style.addLine("Activity")
                style.addLine("BroadcastReceiver")
                style.addLine("Service")
                style.addLine("ContentProvider")
                style.setSummaryText("Android Component")
                builder?.setStyle(style)
            }
            progressBtn -> {
                val runnable = Runnable {
                    for(i in 1..10) {
                        // 닫지 못하게 설정
                        builder?.setAutoCancel(false)
                        builder?.setOngoing(true)
                        builder?.setProgress(10, i, false)
                        manager?.notify(222, builder?.build())
                        if(i >= 10) {
                            manager?.cancel(222)
                        }
                        SystemClock.sleep(1000)
                    }
                }
                val t = Thread(runnable)
                t.start()
            }
            headsupBtn -> {
                builder?.setFullScreenIntent(pendingIntent, true)
            }
            messageBtn -> {
                val sender1 = Person.Builder()
                    .setName("kkang")
                    .setIcon(IconCompat.createWithResource(this, R.drawable.person1))
                    .build()

                val sender2 = Person.Builder()
                    .setName("kim")
                    .setIcon(IconCompat.createWithResource(this, R.drawable.person2))
                    .build()

                // creae image message
                val message = NotificationCompat.MessagingStyle.Message("hello", System.currentTimeMillis(), sender2)

                val style = NotificationCompat.MessagingStyle(sender1)
                    .addMessage("world", System.currentTimeMillis(), sender1)
                    .addMessage(message)

                builder?.setStyle(style)
            }

//            manager?.notify(222, builder?.build())
        }*/

        if (v == bigPictureBtn) {
            // 알림에 이미지 적용하기
            val bigPicture = BitmapFactory.decodeResource(resources, R.drawable.noti_big)
            val bigStyle = NotificationCompat.BigPictureStyle(builder)
            bigStyle.bigPicture(bigPicture)
            builder?.setStyle(bigStyle)
        } else if (v == bigTextBtn) {
            // 알림에 긴 문장 적용
            val bigTextStyle = NotificationCompat.BigTextStyle(builder)
            bigTextStyle.setSummaryText("BigText Summary")
            bigTextStyle.bigText("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라만세!!")
            builder?.setStyle(bigTextStyle)
        } else if (v == inboxBtn) {
            // 알림에 여러 데이터 목록 적용
            val style = NotificationCompat.InboxStyle(builder)
            style.addLine("Activity")
            style.addLine("BroadcastReceiver")
            style.addLine("Service")
            style.addLine("ContentProvider")
            style.setSummaryText("Android Component")
            builder?.setStyle(style)
        } else if (v == progressBtn) {
            val runnable = Runnable {
                for (i in 1..10) {
                    // 닫지 못하게 설정
                    builder?.setAutoCancel(false)
                    builder?.setOngoing(true)
                    builder?.setProgress(10, i, false)
                    manager?.notify(222, builder?.build())
                    if (i >= 10) {
                        // 식별자를 사용해 알림 삭제
                        manager?.cancel(222)
                    }
                    SystemClock.sleep(1000)
                }
            }
            val t = Thread(runnable)
            t.start()
        } else if (v == headsupBtn) {
            // setFullScreenIntent를 사용해 상단에서 알림 띄우기
            builder?.setFullScreenIntent(pendingIntent, true)
        } else if (v == messageBtn) {
            // Person 객체 생성
            val sender1 = Person.Builder()
                .setName("kkang")
                .setIcon(IconCompat.createWithResource(this, R.drawable.person1))
                .build()

            val sender2 = Person.Builder()
                .setName("kim")
                .setIcon(IconCompat.createWithResource(this, R.drawable.person2))
                .build()

            // creae image message
            val message = NotificationCompat.MessagingStyle.Message(
                "hello",
                System.currentTimeMillis(),
                sender2
            )

            val style = NotificationCompat.MessagingStyle(sender1)
                .addMessage("world", System.currentTimeMillis(), sender1)
                .addMessage(message)

            builder?.setStyle(style)
        }

        // 만들어진 알림 상태바 등록(식별자, Builder.build()를 사용해 Notification 객체 생성)
        manager?.notify(222, builder?.build())
    }
}
