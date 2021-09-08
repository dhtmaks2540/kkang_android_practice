package kr.co.lee.part5_mission1

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.contains
import java.io.File
import java.io.FileInputStream
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    lateinit var cameraBtn: ImageView
    lateinit var mainLayout: RelativeLayout

    var externalPermission = false

    // list dialog에 넣을 데이터
    lateinit var dialogDatas: Array<String>
    var filePath: File? = null

    // 부여할 이미지의 크기
    var reqWidth: Int = 0
    var reqHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialogDatas = resources.getStringArray(R.array.main_dialog_list)

        // dimens.xml에 정의되어 있는 크기 얻어오기
        reqWidth = resources.getDimension(R.dimen.request_image_width).toInt()
        reqHeight = resources.getDimension(R.dimen.request_image_height).toInt()

        // View 초기화
        mainLayout = findViewById(R.id.main_content)
        cameraBtn = findViewById(R.id.main_photo_icon)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            externalPermission = true
        }

        cameraBtn.setOnClickListener {
            if (!externalPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    100
                )
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("촬영")
                builder.setItems(dialogDatas, DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        0 -> {
                            try {
                                // External storage의 절대경로 받아서 그 안에 myApp 폴더 경로 지정
                                val dirPath = this.getExternalFilesDir(null)?.absolutePath + "/myApp"
//                                Environment.getExternalStorageDirectory().absolutePath + "/myApp"
                                val dir = File(dirPath)

                                if (!dir.exists()) {
                                    dir.mkdir()
                                }

                                filePath = File.createTempFile("IMG", ".jpg", dir)
                                if (!filePath?.exists()!!) {
                                    filePath?.createNewFile()
                                }

                                val photoUri = FileProvider.getUriForFile(
                                    this,
                                    "${BuildConfig.APPLICATION_ID}.FileProvider",
                                    filePath!!
                                )
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                                startActivityForResult(intent, 40)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        1 -> {
                            try {
                                val dirPath = this.getExternalFilesDir(null)?.absolutePath + "/myApp"
//                                    Environment.getExternalStorageDirectory().absolutePath + "/myApp"
                                val dir = File(dirPath)

                                if (!dir.exists()) {
                                    dir.mkdir()
                                }

                                filePath = File.createTempFile("VIDEO", ".mp4", dir)
                                if (!filePath?.exists()!!) {
                                    filePath?.createNewFile()
                                }

                                val videoUri = FileProvider.getUriForFile(
                                    this,
                                    "${BuildConfig.APPLICATION_ID}.FileProvider",
                                    filePath!!
                                )
                                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0)
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20)
                                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024 * 1024 * 10)
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)
                                startActivityForResult(intent, 50)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                })

                val listDialog = builder.create()
                listDialog.show()
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView = ImageView(this)
        val videoView = VideoView(this)

        if (requestCode == 40 && resultCode == RESULT_OK) {
            if (filePath != null) {
                // imageView, videoView 지우기
                if (mainLayout.contains(imageView)) {
                    mainLayout.removeView(imageView)
                }
                if (mainLayout.contains(videoView)) {
                    mainLayout.removeView(videoView)
                }

                // BitmapFactory에서 이미지를 만들 때 사용하는 옵션
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                try {
                    // Path로부터 FileInputStream을 가져온다
                    var fileIn: FileInputStream? = FileInputStream(filePath)
                    BitmapFactory.decodeStream(fileIn, null, options)
                    fileIn?.close()
                    fileIn = null
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // Bitmap을 생성하며 사용한 옵션을 통해 실제 이미지의 크기를 얻어온다
                val height = options.outHeight
                val width = options.outWidth
                var inSampleSize = 1

                // 원하는 이미지의 크기와 실제 이미지의 크기를 비율로 조정
                if (height > reqHeight || width > reqWidth) {
                    val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
                    val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()

                    inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
                }

                val imgOptions = BitmapFactory.Options()
                // 이미지의 크기를 조절하기 위한 비율
                imgOptions.inSampleSize = inSampleSize
                val bitmap = BitmapFactory.decodeFile(filePath?.absolutePath, imgOptions)

                imageView.setImageBitmap(bitmap)

                val params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                mainLayout.addView(imageView, params)
            }
        } else if (requestCode == 50 && resultCode == RESULT_OK) {
            if (filePath != null) {
                // imageView, videoView 지우기
                if (mainLayout.contains(imageView)) {
                    mainLayout.removeView(imageView)
                }
                if (mainLayout.contains(videoView)) {
                    mainLayout.removeView(videoView)
                }

                // 기본 동영상 controller 제공
                videoView.setMediaController(MediaController(this))
                // 동영상 파일 경로 지정
                val videoUri = Uri.parse(filePath?.absolutePath)
                // 동영상 정보 획득 객체
                val retriever = MediaMetadataRetriever()
                var bmp: Bitmap? = null
                retriever.setDataSource(filePath?.absolutePath)
                bmp = retriever.getFrameAtTime()
                // 동영상 가로세로 사이즈 획득
                val videoHeight = bmp?.height
                val videoWidth = bmp?.width
                // VideoView에 동영상 정보 지정
                videoView.setVideoURI(videoUri)
                // VideoView의 화면 출력 가로 세로 사이즈 지정
                if (videoWidth!! >= videoHeight!!) {
                    val params = RelativeLayout.LayoutParams(reqWidth, reqHeight)
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    mainLayout.addView(videoView)
                } else {
                    val params = RelativeLayout.LayoutParams(reqHeight, reqWidth)
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    mainLayout.addView(videoView)
                }

                // 동영상 플레이
                videoView.start()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                externalPermission = true
            }
        }
    }
}