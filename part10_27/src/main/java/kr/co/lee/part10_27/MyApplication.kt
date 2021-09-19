package kr.co.lee.part10_27

import android.app.Application
import android.content.Context

class MyApplication: Application() {
    companion object {
        lateinit var context: Context

        fun getAppContext(): Context = context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

//    private var context: Context? = null
//
//    override fun onCreate() {
//        super.onCreate()
//        MyApplication.context = applicationContext
//    }
//
//    fun getAppContext(): Context? {
//        return MyApplication.context
//    }
}