package kr.co.lee.part9_25

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object {
        const val BASE_URL: String = "https://newsapi.org"

        // BaseUrl로 기본 서버 URL 지정
        // addConverterFactory로 서버와 통신할 데이터 타입에 맞는 컨버터 지정
        fun create(): RetrofitService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            // create를 사용하면 Call 객체를 가지는 Service 객체가 자동으로 만들어 진다
            return retrofit.create(RetrofitService::class.java)
        }
    }
}