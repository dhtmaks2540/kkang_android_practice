package kr.co.lee.part5_mission1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Mission2_Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var callBtn: ImageView
    lateinit var locationBtn: ImageView
    lateinit var shareBtn: ImageView
    lateinit var internetBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission2)

        callBtn = findViewById(R.id.mission2_call)
        locationBtn = findViewById(R.id.mission2_location)
        shareBtn = findViewById(R.id.mission2_share)
        internetBtn = findViewById(R.id.mission2_internet)

        callBtn.setOnClickListener(this)
        locationBtn.setOnClickListener(this)
        shareBtn.setOnClickListener(this)
        internetBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            callBtn -> {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:02-120"))
                    startActivity(intent)
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 10)
                }
            }
            locationBtn -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451?q=37.5662952, 126.9779451"))
                startActivity(intent)
            }
            internetBtn -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.seoul.go.kr"))
                startActivity(intent)
            }
        }
    }
}