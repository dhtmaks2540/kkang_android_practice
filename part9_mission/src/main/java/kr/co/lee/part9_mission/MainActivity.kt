package kr.co.lee.part9_mission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val API_KEY = "ef044af6df15116d8a1bd84399fcd788"

    lateinit var container: ConstraintLayout
    lateinit var maxTemp: TextView
    lateinit var minTemp: TextView
    lateinit var nowTemp: TextView
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.main_container)
        maxTemp = findViewById(R.id.main_max_value)
        minTemp = findViewById(R.id.main_min_value)
        nowTemp = findViewById(R.id.main_temp_value)
        recycler = findViewById(R.id.main_recycler)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler.layoutManager = linearLayoutManager

        // Service 객체 획득
        val networkService = RetrofitFactory.create()
        // 오늘 날씨 Call 객체
        val todayMap = HashMap<String, String>()
        todayMap["q"] = "seoul"
        todayMap["mode"] = "json"
        todayMap["units"] = "metric"
        todayMap["appid"] = API_KEY

        networkService.getTodayWeather(todayMap)
            .enqueue(object: Callback<Any> {
                // 정상적으로 결과 받음
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    // 응답이 왔다면
                    if(response.isSuccessful) {
                        val body = response.body()
                        val gson = GsonBuilder().create()
                        val json = gson.toJson(body)
                        val root = JSONObject(json)
                        val main = root.getJSONObject("main")
                        val array = root.getJSONArray("weather")

                        maxTemp.text = main.getString("temp_max")
                        minTemp.text = main.getString("temp_min")
                        nowTemp.text = main.getString("temp")

                        val imageUrl = array.getJSONObject(0).getString("icon")

//                        Glide.with(this@MainActivity).load("http://openweathermap.org/img/w/${imageUrl}.png")
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    showMessage(t.message.toString())
                }

            })

        val weekendMap = HashMap<String, String>()
        weekendMap["q"] = "seoul"
        weekendMap["mode"] = "json"
        weekendMap["units"] = "metric"
        weekendMap["appid"] = API_KEY
        networkService.getWeekWeather(weekendMap)
            .enqueue(object: Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        val gson = GsonBuilder().create()
                        val json = gson.toJson(response.body())
                        val root = JSONObject(json)

                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    showMessage(t.message.toString())
                }

            })
    }

    private fun showMessage(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}