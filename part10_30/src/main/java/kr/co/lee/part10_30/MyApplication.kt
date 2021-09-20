package kr.co.lee.part10_30

import android.app.Application
import android.content.Context
import androidx.room.Room
import kr.co.lee.part10_30.retrofit.RetrofitFactory
import kr.co.lee.part10_30.retrofit.RetrofitService
import kr.co.lee.part10_30.room.AppDatabase
import kr.co.lee.part10_30.room.ArticleDAO

class MyApplication: Application() {
    companion object {
        lateinit var context: Context
        lateinit var dao: ArticleDAO
        lateinit var networkService: RetrofitService

        fun getAppContext(): Context? {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
        dao = db.articleDao()
        networkService = RetrofitFactory.create()
    }
}