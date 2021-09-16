package kr.co.lee.part9_25

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 네트워킹 시 호출해야 할 함수 등록
interface RetrofitService {
    // Get 방식(url)
    @GET("/v2/everything")
    // Query를 사용해 질의
    fun getList(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int
    ):
            Call<PageListModel>
}