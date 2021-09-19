package kr.co.lee.part10_28

import android.app.Application
import android.content.Context

class MyApplication: Application() {
    companion object {
        private lateinit var context: Context

        fun getAppContext(): Context? {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}