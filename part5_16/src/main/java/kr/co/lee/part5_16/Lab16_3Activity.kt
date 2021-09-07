package kr.co.lee.part5_16

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlin.random.Random

class Lab16_3Activity : AppCompatActivity() {
    lateinit var oneThread:  OneThread
    lateinit var oddDatas: ArrayList<String>
    lateinit var evenDatas: ArrayList<String>

    lateinit var oddAdapter: ArrayAdapter<String>
    lateinit var evenAdapter: ArrayAdapter<String>

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab16_3)

        val oddListiew = findViewById<ListView>(R.id.lab3_list_odd)
        val evenListView = findViewById<ListView>(R.id.lab3_list_even)

        oddDatas = ArrayList()
        evenDatas = ArrayList()

        oddAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, oddDatas)
        evenAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, evenDatas)

        oddListiew.adapter = oddAdapter
        evenListView.adapter = evenAdapter

        handler = Handler()

        oneThread = OneThread()
        oneThread.start()

        val twoThread = TwoThread()
        twoThread.start()
    }

    inner class OneThread: Thread() {
        var oneHandler: Handler? = null

        override fun run() {
            Looper.prepare()
            oneHandler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    SystemClock.sleep(1000)
                    val data = msg.arg1
                    if(msg.what == 0) {
                        handler.post {
                            evenDatas.add("even : $data")
                            evenAdapter.notifyDataSetChanged()
                        }
                    } else if(msg.what == 1) {
                        handler.post {
                            oddDatas.add("odd : $data")
                            oddAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            Looper.loop()
        }
    }

    inner class TwoThread: Thread() {
        override fun run() {
            val random = java.util.Random()
            for(i in 0 until 10) {
                SystemClock.sleep(1000)
                val data = random.nextInt(10)
                val message = Message()
                if(data % 2 == 0) {
                    message.what = 0
                } else {
                    message.what = 1
                }
                message.arg1 = data
                message.arg2 = 1
                oneThread.oneHandler?.sendMessage(message)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        oneThread.oneHandler?.looper?.quit()
    }
}