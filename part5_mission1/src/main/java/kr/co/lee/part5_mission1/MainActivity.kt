package kr.co.lee.part5_mission1

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var cameraBtn: ImageView
    var externalPermission = false
    lateinit var dialogDatas: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialogDatas = resources.getStringArray(R.array.main_dialog_list)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            externalPermission = true
        }

        cameraBtn = findViewById(R.id.main_photo_icon)
        cameraBtn.setOnClickListener {
            if(!externalPermission) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 200)
                if(!externalPermission) return@setOnClickListener
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle("촬영")
            builder.setItems(dialogDatas, DialogInterface.OnClickListener { dialog, which ->
                when(which) {
                    0 -> {

                    }
                }
            })
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 200 && grantResults.isNotEmpty()) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                externalPermission = true
            }
        }
    }
}