package kr.co.lee.part7_19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DialogActivity : AppCompatActivity() {

    lateinit var finishBtn: ImageView
    lateinit var numberView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        numberView = findViewById(R.id.lab1_phone_number)
        finishBtn = findViewById(R.id.lab1_remove_icon)

        finishBtn.setOnClickListener {
            finish()
        }

        val intent = intent
        val number = intent.getStringExtra("number")
        numberView.text = number
    }
}