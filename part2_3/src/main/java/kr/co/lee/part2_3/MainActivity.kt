package kr.co.lee.part2_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linear = LinearLayout(this)

        val bt = Button(this)
        bt.text = "Button1"
        linear.addView(bt)

        val bt2 = Button(this)
        bt2.text = "Button2"
        linear.addView(bt2)

        setContentView(linear)
    }
}