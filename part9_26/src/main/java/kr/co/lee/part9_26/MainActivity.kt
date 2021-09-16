package kr.co.lee.part9_26

import android.content.Context
import android.graphics.Color
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class MainActivity : AppCompatActivity() {

    lateinit var list: ArrayList<ChatMessage>
    lateinit var adapter: MyAdapter

    lateinit var listView: ListView
    lateinit var sendBtn: ImageView
    lateinit var msgEdit: EditText

    var flagConnection = true
    var isConnected = false
    var flagRead = true

    var writeHandler: Handler? = null

    var socket: Socket? = null
    var bin: BufferedInputStream? = null
    var bout: BufferedOutputStream? = null

    var st: SocketThread? = null
    var rt: ReadThread? = null
    var wt: WriteThread? = null

    var serverIp = "192.168.0.9"
    var serverPort = 7070

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lab1_list)
        sendBtn = findViewById(R.id.lab1_send_btn)
        msgEdit = findViewById(R.id.lab1_send_text)

        sendBtn.setOnClickListener {
            if(msgEdit.text.toString().trim() != "") {
                val msg = Message()
                msg.obj = msgEdit.text.toString()
                // WriteHandler에게 메세지 보내기
                writeHandler?.sendMessage(msg)
            }
        }

        list = ArrayList()
        adapter = MyAdapter(this, R.layout.chat_item, list)
        listView.adapter = adapter

        sendBtn.isEnabled = false
        msgEdit.isEnabled = false
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    // 메세지 보내기 기능
    private fun addMessage(who: String, msg: String) {
        val vo = ChatMessage(who, msg)
        list.add(vo)
        adapter.notifyDataSetChanged()
        listView.setSelection(list.size - 1)
    }
    
    override fun onStart() {
        super.onStart()
        // SocketThread 실행
        st = SocketThread()
        st?.start()
    }

    override fun onStop() {
        super.onStop()

        flagConnection = false
        isConnected = false

        if(socket != null) {
            flagRead = false
            writeHandler?.looper?.quit()
            try {
                bout?.close()
                bin?.close()
                socket?.close()
            } catch (e: IOException) {

            }
        }
    }

    var mainHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            // 넘어오는 메시지의 what에 따라 상황 분기
            if (msg.what == 10) {
                // 연결 완료
                showToast("connection ok~~")
                sendBtn.isEnabled = true
                msgEdit.isEnabled = true
            } else if (msg.what == 20) {
                showToast("connection fail~~")
                sendBtn.isEnabled = false
                msgEdit.isEnabled = false
            } else if (msg.what == 100) {
                addMessage("you", msg.obj as String)
            } else if (msg.what == 200) {
                addMessage("me", msg.obj as String)
            }
        }
    }

    inner class SocketThread: Thread() {
        override fun run() {
            // Connection이 True인 동안
            while (flagConnection) {
                try {
                    // 만약 연결이 안되어 있다면
                    if(!isConnected) {
                        socket = Socket()
                        // 연결 서버
                        val remoteAddr = InetSocketAddress(serverIp, serverPort)
                        // connect를 사용해 서버에 연결 요청
                        socket?.connect(remoteAddr, 10000)

                        // IO 객체 생성
                        bout = BufferedOutputStream(socket?.getOutputStream())
                        bin = BufferedInputStream(socket?.getInputStream())

                        // ReadThread가 널이 아니라면
                        if(rt != null) {
                            flagRead = false
                        }
                        // WriteThread가 널이 아니라면
                        if(wt != null) {
                            // loopert 종료
                            writeHandler?.looper?.quit()
                        }

                        // WriteThread, ReadThread 시작
                        wt = WriteThread()
                        wt?.start()
                        rt = ReadThread()
                        rt?.start()

                        // 연결 true 설정
                        isConnected = true

                        // Message객체 생성 후 mainHandler에 Message 보내기
                        val msg = Message()
                        msg.what = 10
                        mainHandler.sendMessage(msg)
                    } else {
                        // 연결이 되어 있을때는 10초 대기
                        SystemClock.sleep(10000)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    SystemClock.sleep(10000)
                }
            }
        }
    }

    inner class WriteThread: Thread() {
        override fun run() {
            // 개발자 쓰레드끼리 메시지를 보내고 하기 위해 Looper 객체 생성(메시지큐에 메시지들 감지)
            Looper.prepare()
            // 핸들러 생성
            writeHandler = object: Handler() {
                override fun handleMessage(msg: Message) {
                    try {
                        // 데이터를 송신
                        bout?.write((msg.obj as String).toByteArray())
                        bout?.flush()

                        // Message 객체 생성 후 mainHandler에게 전달
                        val message = Message()
                        message.what = 200
                        message.obj = msg.obj
                        mainHandler.sendMessage(message)
                    } catch(e: Exception) {
                        e.printStackTrace()
                        isConnected = false
                        writeHandler?.looper?.quit()
                        try {
                            flagRead = false
                        } catch(e1: Exception) {

                        }
                    }
                }
            }
            Looper.loop()
        }
    }

    inner class ReadThread: Thread() {
        override fun run() {
            var buffer: ByteArray? = null
            // flagRead가 True인 동안
            while (flagRead) {
                buffer = ByteArray(1024)
                try {
                    // 데이터를 수신
                    var message: String? = null
                    val size = bin!!.read(buffer)
                    // 받은 데이터를 mainHandler에게 전달
                    if (size > 0) {
                        message = String(buffer, 0, size, charset("utf-8"))
                        if (message != null && message != "") {
                            val msg = Message()
                            msg.what = 100
                            msg.obj = message
                            mainHandler.sendMessage(msg)
                        }
                    } else {
                        flagRead = false
                        isConnected = false
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    flagRead = false
                    isConnected = false
                }
            }
            val msg = Message()
            msg.what = 20
            mainHandler.sendMessage(msg)
        }
    }
}

data class ChatMessage(val who: String, val msg: String) {

}

class MyAdapter(val adContext: Context, val resId: Int, val list: ArrayList<ChatMessage>): ArrayAdapter<ChatMessage>(adContext, resId, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // LayoutInflater를 이용해 layout 초기화
        val inflater = adContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        convertView = inflater.inflate(resId, null)

        val msgView = convertView.findViewById<TextView>(R.id.lab1_item_msg)
        // 뷰와 관련된 layout parameter를 가져와서 RelativeLayout의 LayoutParams로 형변환
        val params = msgView.layoutParams as RelativeLayout.LayoutParams

        // who의 타입에 따라 UI 분기 
        val msg = list[position]
        if(msg.who == "me") {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
            msgView.setTextColor(Color.WHITE)
            msgView.setBackgroundResource(R.drawable.chat_right)
        } else if(msg.who == "you") {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
            msgView.setBackgroundResource(R.drawable.chat_left)
        }
        msgView.text = msg.msg

        return convertView
    }
}