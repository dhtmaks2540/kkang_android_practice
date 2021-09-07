package kr.co.lee.part5_16

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var startView: ImageView
    lateinit var pauseView: ImageView
    lateinit var textView: TextView

    var loopFlag = true
    var isFirst = true
    var isRun = false

    lateinit var thread: MyThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startView = findViewById(R.id.main_startBtn)
        pauseView = findViewById(R.id.main_pauseBtn)
        textView = findViewById(R.id.main_textView)

        startView.setOnClickListener(this)
        pauseView.setOnClickListener(this)

        thread = MyThread()
    }

    override fun onClick(v: View?) {
        when(v) {
            startView -> {
                if(isFirst) {
                    isFirst = false
                    isRun = true
                    thread.start()
                } else {
                    isRun = true
                }
            }
            pauseView -> {
                isRun = false
            }
        }
    }

    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if(msg.what == 1) {
                textView.text = msg.arg1.toString()
            } else if(msg.what == 2) {
                textView.text = msg.obj.toString()
            }
        }
    }

    inner class MyThread: Thread() {
        override fun run() {
            try {
                var count = 10
                while(loopFlag) {
                    sleep(1000)
                    if(isRun) {
                        count--
                        var message = Message()
                        message.what = 1
                        message.arg1 = count
                        handler.sendMessage(message)
                        if(count == 0) {
                            // 종료
                            message = Message()
                            message.what = 2
                            message.obj = "Finish!"
                            handler.sendMessage(message)
                            // thread가 종료되게 설정
                            loopFlag=false
                        }
                    }
                }
            } catch (e: Exception) {
                
            }
        }
    }
}