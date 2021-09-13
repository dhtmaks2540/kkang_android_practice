package kr.co.lee.part7_21

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Lab21_2Activity : AppCompatActivity() {

    lateinit var contactBtn: Button
    lateinit var nameView: TextView
    lateinit var phoneView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab21_2)

        contactBtn = findViewById(R.id.lab2_btn)
        nameView = findViewById(R.id.lab2_name)
        phoneView = findViewById(R.id.lab2_phone)

        val listener = View.OnClickListener {
            when(it) {
                contactBtn -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.data = Uri.parse("content://com.android.contacts/data/phones")
                    startActivityForResult(intent, 10)
                }
            }
        }

        contactBtn.setOnClickListener(listener)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 100)
        }
    }
}