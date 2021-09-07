package kr.co.lee.part3_9

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kr.co.lee.part3_9.databinding.ActivityMainBinding
import java.io.File
import java.io.FileWriter
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var fileReadPermission: Boolean = false
    var fileWritePErmission: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Permission 체크
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            fileReadPermission = true
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            fileWritePErmission = true
        }

        // permission 부여 안 될경우 permission 요청
        if(!fileReadPermission || !fileWritePErmission) {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 200)
        }

        val listener = View.OnClickListener {
            val content = binding.content.text.toString()
            // 퍼미션이 부여되어 있다면
            if(fileReadPermission && fileWritePErmission) {
                val writer: FileWriter
                try {
                    // 외부 저장 공간 root 하위에 myApp이라는 폴더 경로 획득
                    val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/myApp"
                    val dir = File(dirPath)
                    // 폴더가 없으면 새로 만든다
                    if(!dir.exists()) {
                        dir.mkdir()
                    }
                    // myApp폴더 밑에 myfile.txt 파일 저장
                    val file = File(dirPath + "/myfile.txt")
                    // 파일이 없다면 새로 만들어준다
                    if(!file.exists()) {
                        file.createNewFile()
                    }
                    // file write
                    writer = FileWriter(file, true)
                    writer.write(content)
                    writer.flush()
                    writer.close()

                    // 결과 확인을 위한 FileReadActivity 실행 크래스
                    val intent = Intent(this, ReadFileActivity::class.java)
                    // FileReadActivity로 화면 전환
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                showToast("permission이 부여 안되어 기능을 실행할 수 없습니다")
            }
        }

        binding.btn.setOnClickListener(listener)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 200 && grantResults.isNotEmpty()) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                fileReadPermission = true
            if(grantResults[1] == PackageManager.PERMISSION_GRANTED)
                fileWritePErmission = true
        }
    }
}