package kr.co.lee.part4_13

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kr.co.lee.part4_13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val plusView = findViewById<MyPlusView>(R.id.customView)
        val barView = findViewById<View>(R.id.barView)

        plusView.setOnMyChangeListener(object : OnMyChangeListener {
            override fun onChange(value: Int) {
                if (value < 0) {
                    barView.setBackgroundColor(Color.RED)
                } else if (value < 30) {
                    barView.setBackgroundColor(Color.YELLOW)
                } else if (value < 60) {
                    barView.setBackgroundColor(Color.BLUE)
                } else {
                    barView.setBackgroundColor(Color.GREEN)
                }
            }

        })
    }
}