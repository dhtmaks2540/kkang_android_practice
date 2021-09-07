package kr.co.lee.part5_14

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.viewbinding.BuildConfig
import kr.co.lee.part5_14.databinding.ActivityLab142Binding
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class Lab14_2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab142Binding
    var filePath: File? = null

    var reqWidth = 0
    var reqHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLab142Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val listener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                when(v) {
                    binding.btnContacts -> {
                        // 주소록 목록 띄우기
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                        startActivityForResult(intent, 10)
                    }
                    binding.btnCameraData -> {
                        // 카메라 앱 intent - data 획득
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, 30)
                    }
                    binding.btnCameraFile -> {
                        // 카메라 앱 intent - file 공유
                        if(ContextCompat.checkSelfPermission(this@Lab14_2Activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_GRANTED) {
                            try {
                                val dirPath = Environment.getExternalStorageDirectory().
                                        absolutePath + "/myApp"
                                val dir = File(dirPath)
                                if(!dir.exists()) {
                                    dir.mkdir()
                                }

                                filePath = File.createTempFile("IMG", ".jpg", dir)
                                if(!filePath?.exists()!!) {
                                    filePath?.createNewFile()
                                }
                                val photoURI = FileProvider.getUriForFile(this@Lab14_2Activity, "${application.packageName}.provider", filePath!!)
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                startActivityForResult(intent, 40)
                            } catch(e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            ActivityCompat.requestPermissions(this@Lab14_2Activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
                        }
                    }
                    binding.btnSpeech -> {
                        // 음성인식
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성인식 테스트")
                        startActivityForResult(intent, 50)
                    }
                    binding.btnMap -> {
                        // 지도 연동
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"))
                        startActivity(intent)
                    }
                    binding.btnBrowser -> {
                        // 브라우저
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.seoul.go.kr"))
                        startActivity(intent)
                    }
                    binding.btnCall -> {
                        // Call
                        if (ContextCompat.checkSelfPermission(this@Lab14_2Activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:02-120"))
                            startActivity(intent)
                        } else {
                            ActivityCompat.requestPermissions(this@Lab14_2Activity, arrayOf(Manifest.permission.CALL_PHONE), 100)
                        }
                    }
                    binding.resultImageView -> {
                        // 사진 보여주기
                        val intent = Intent()
                        intent.setAction(Intent.ACTION_VIEW)
                        val photoURI = FileProvider.getUriForFile(this@Lab14_2Activity, "${application.packageName}.provider", filePath!!)
                        intent.setDataAndType(photoURI, "image/*")
                        // 외부 앱에서 우리 데이터를 이용하는 것이므로
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        startActivity(intent)
                    }
                }
            }

        }

        binding.btnBrowser.setOnClickListener(listener)
        binding.btnCall.setOnClickListener(listener)
        binding.btnCameraData.setOnClickListener(listener)
        binding.btnCameraFile.setOnClickListener(listener)
        binding.btnContacts.setOnClickListener(listener)
        binding.btnMap.setOnClickListener(listener)
        binding.btnSpeech.setOnClickListener(listener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10 && resultCode == RESULT_OK) {
            val result = data?.dataString
            binding.resultView.text = result
        } else if(requestCode == 30 && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            binding.resultImageView.setImageBitmap(bitmap)
        } else if(requestCode == 40 && resultCode == RESULT_OK) {
            if(filePath != null) {
               val options = BitmapFactory.Options()
               options.inJustDecodeBounds = true
               try {
                   var fileIn: InputStream? = FileInputStream(filePath)
                   BitmapFactory.decodeStream(fileIn, null, options)
                   fileIn?.close()
                   fileIn = null
               } catch(e: Exception) {
                   e.printStackTrace()
               }

                val height = options.outHeight
                val width = options.outWidth
                var inSampleSize = 1
                if(height > reqHeight || width > reqWidth) {
                    val heightRatio: Int = Math.round(height.toFloat() / reqHeight.toFloat())
                    val widthRatio: Int = Math.round(width.toFloat() / reqWidth.toFloat())

                    inSampleSize = if(heightRatio < widthRatio) heightRatio else widthRatio
                }

                val imgOptions = BitmapFactory.Options()
                imgOptions.inSampleSize = inSampleSize
                val bitmap = BitmapFactory.decodeFile(filePath?.absolutePath, imgOptions)
                binding.resultImageView.setImageBitmap(bitmap)
            }
        } else if(requestCode == 50 && resultCode == RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val result = results?.get(0)
            binding.resultView.text = result
        }
    }
}