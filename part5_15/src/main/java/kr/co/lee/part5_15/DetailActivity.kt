package kr.co.lee.part5_15

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity: AppCompatActivity(){

    var count = 0
    lateinit var countView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        countView = findViewById(R.id.detail_count)
        val btn = findViewById<Button>(R.id.detail_btn)

        btn.setOnClickListener {
            count++
            countView.text = count.toString()
        }
    }
}