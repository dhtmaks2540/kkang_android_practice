package kr.co.lee.part9_25

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Lab25_2Activity : AppCompatActivity() {

    lateinit var titleView: TextView
    lateinit var dateView: TextView
    lateinit var contentView: TextView
    lateinit var imageView: NetworkImageView

    var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab25_2)

        titleView = findViewById(R.id.lab2_title)
        dateView = findViewById(R.id.lab2_date)
        contentView = findViewById(R.id.lab2_content)

        imageView = findViewById(R.id.lab2_image)
        // RequestQueue 생성
        queue = Volley.newRequestQueue(this)

        val responseListener = Response.Listener<JSONObject> {
            // 서버 응답 후 사후 처리
            // JSONObject에서 데이터 획득
            try {
                titleView.text = it.getString("title")
                dateView.text = it.getString("date")
                contentView.text = it.getString("content")

                val imageFile = it.getString("file")
                if(imageFile != null && !imageFile.equals("")) {
                    // NetWorkImageView를 레이아웃에 등록한 후 코드에서 이 뷰를 이용해 서버에 직접 요청
                    val imageLoader = ImageLoader(queue, object : ImageLoader.ImageCache {
                        override fun getBitmap(url: String?): Bitmap? {
                            return null
                        }

                        // 서버 이미지 캐싱
                        override fun putBitmap(url: String?, bitmap: Bitmap?) {

                        }

                    })

                    // 이미지 요청
                    imageView.setImageUrl("http://192.168.0.9:8000/files/$imageFile", imageLoader)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val responseErrorListener = Response.ErrorListener {

        }

        // Json Request 정보를 담은 JsonRequester 생성
        val jsonRequest = JsonObjectRequest(Request.Method.GET, "http://192.168.0.9:8000/files/test.json", null, responseListener, responseErrorListener)
        // RequestQueue에 add 함수에 매개변수로 지정해 호출하면 서버 연동이 발생
        // 만든 jsonRequest를 add한다
        queue?.add(jsonRequest)
    }
}