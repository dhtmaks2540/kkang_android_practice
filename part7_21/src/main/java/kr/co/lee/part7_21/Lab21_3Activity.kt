package kr.co.lee.part7_21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class Lab21_3Activity : AppCompatActivity() {

    lateinit var gifView: ImageView
    lateinit var networkView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab21_3)

        gifView = findViewById(R.id.lab3_gif)
        networkView = findViewById(R.id.lab3_network)

        Glide.with(this)
            .load(R.raw.loading)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .override(200, 200)
            .into(gifView)

        val url = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"
        Glide.with(this)
            .load(url)
            .override(400, 400)
            .error(R.drawable.error)
            .into(networkView)
    }
}