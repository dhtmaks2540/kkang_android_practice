package kr.co.lee.part9_26

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstanceIDService: FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        // Fcm 서버로부터 얻은 키 값
        val token = FirebaseMessaging.getInstance().token
        Log.d("Change!!!!", "token : $token")
    }
}