package kr.co.lee.part10_30.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object {
        private const val BASE_URL = "https://newsapi.org"

        fun create(): RetrofitService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RetrofitService::class.java)
        }
    }
}