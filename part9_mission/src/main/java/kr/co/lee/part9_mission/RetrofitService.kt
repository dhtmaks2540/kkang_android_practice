package kr.co.lee.part9_mission

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitService {

    @GET("weather")
    fun getTodayWeather(@QueryMap options: Map<String, String>): Call<Any>

    @GET("forecast/daily")
    fun getWeekWeather(@QueryMap options: Map<String, String>): Call<Any>
}