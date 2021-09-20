package kr.co.lee.part10_30.retrofit

import kr.co.lee.part10_29.model.PageListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/v2/everything")
    fun getList(@Query("q") q: String, @Query("apiKey") apiKey: String, @Query("page") page: Long, @Query("pageSize") pageSize: Int): Call<PageListModel>
}