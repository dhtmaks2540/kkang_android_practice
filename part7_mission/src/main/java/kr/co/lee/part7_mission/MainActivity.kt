package kr.co.lee.part7_mission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        const val READ_EXTERNAL_PERMISSION = 100
        const val GALLERY_CODE = 30
    }

    lateinit var editText: EditText
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.mission1_edit)
        toolbar = findViewById(R.id.mission1_toolbar)

        setSupportActionBar(toolbar)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_PERMISSION
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_gallery -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, GALLERY_CODE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_CODE) {
            if (resultCode == RESULT_OK) {
                var currentImageUri = data?.data
                try {
                    currentImageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                currentImageUri
                            )

                            val metrics = resources.displayMetrics
                            val widthPx = metrics.widthPixels
                            val heightPx = metrics.heightPixels
                            val widthDp = metrics.xdpi
                            val heightDp = metrics.ydpi

                            val options = BitmapFactory.Options()
                            options.inJustDecodeBounds = true

//                            bitmap.width = widthPx

                            val stringBuilder = SpannableStringBuilder(editText.text)
                            stringBuilder.insert(editText.selectionStart, "\n[[$bitmap]] \n")

                            val result = stringBuilder.toString()
                            val start = result.indexOf("[[$bitmap]]")
                            val end = start + "[[${bitmap}]]".length

                            stringBuilder.setSpan(ImageSpan(this, bitmap, ImageSpan.ALIGN_BASELINE),
                                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                            editText.text = stringBuilder
                        } else {
                            val source = ImageDecoder.createSource(this.contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)

                            val metrics = resources.displayMetrics
                            val widthPx = metrics.widthPixels
                            val heightPx = metrics.heightPixels
                            val widthDp = metrics.xdpi
                            val heightDp = metrics.ydpi

                            val options = BitmapFactory.Options()
                            options.inJustDecodeBounds = true

//                            bitmap.width = widthPx

                            val stringBuilder = SpannableStringBuilder(editText.text)
                            stringBuilder.insert(editText.selectionStart, "\n[[" + bitmap + "]] \n")

                            val result = stringBuilder.toString()
                            val start = result.indexOf("[[" + bitmap + "]]")
                            val end = start + "[[${bitmap}]]".length

                            stringBuilder.setSpan(ImageSpan(this, bitmap, ImageSpan.ALIGN_BASELINE),
                                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                            editText.text = stringBuilder
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else if (resultCode == RESULT_CANCELED) {
                val toast = Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

}