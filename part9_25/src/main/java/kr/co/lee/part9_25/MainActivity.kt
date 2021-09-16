package kr.co.lee.part9_25

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var titleView: TextView
    lateinit var dateView: TextView
    lateinit var contentView: TextView
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleView = findViewById(R.id.lab1_title)
        dateView = findViewById(R.id.lab1_date)
        contentView = findViewById(R.id.lab1_content)
        imageView = findViewById(R.id.lab1_image)

        // 서버에 전송할 데이터
        val map = HashMap<String, String>()
        map["name"] = "kkang"

        // 문자열 http 요청
        // URL과 데이터, Callback 인터페이스를 넘겨주며 Http 연결
        val httpRequester = HttpRequester()
        httpRequester.request("http://192.168.0.9:8000/files/test.json", map, httpCallback)
    }
    
    // 문자열 결과 획득 callback
    val httpCallback = object : HttpCallback {
        override fun onResult(result: String?) {
            // 결과 json parsing
            try {
                // Json 데이터의 루트 데이터
                val root = JSONObject(result)
                titleView.text = root.getString("title")
                dateView.text = root.getString("date")
                contentView.text = root.getString("content")

                val imageFile = root.getString("file")
                if(imageFile != null && !imageFile.equals("")) {
                    // 결과 문자열에 이미지 파일 정보가 있다면 다시 이미지 데이터 요청
                    val imageRequester = HttpImageRequester()
                    imageRequester.request("http://192.168.0.9:8000/files/$imageFile", null, imageCallback)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 획득한 이미지 데이터를 받기 위한 callback
    val imageCallback = object : HttpImageCallback {
        override fun onResult(d: Bitmap?) {
            imageView.setImageBitmap(d)
        }
    }
}