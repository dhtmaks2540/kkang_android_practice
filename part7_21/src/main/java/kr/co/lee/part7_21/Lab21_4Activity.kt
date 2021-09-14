package kr.co.lee.part7_21

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine

class Lab21_4Activity : FragmentActivity() {

    lateinit var btn1: Button
    lateinit var imageView: ImageView
    lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab21_4)

        btn1 = findViewById(R.id.lab4_btn)
        linearLayout = findViewById(R.id.lab4_content)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
        }

        btn1.setOnClickListener {
            Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG)) // 모든 타입 선택
                .countable(true) // 몇 개를 선택했는지 개수 보임
                .maxSelectable(9) // 최대 선택 개수
                .spanCount(3)
                .imageEngine(GlideEngine())
                .forResult(100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK) {
            val mSelected = Matisse.obtainResult(data)
            for(uri in mSelected) {
                val imageView = ImageView(this)
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                linearLayout.addView(imageView, params)
                Glide.with(this)
                    .load(uri)
                    .override(400, 400)
                    .into(imageView)
            }
        }
    }
}