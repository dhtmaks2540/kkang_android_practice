package kr.co.lee.part7_19

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotiReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val toast = Toast.makeText(context, "I am NotiReceiver...", Toast.LENGTH_SHORT)
        toast.show()
    }
}