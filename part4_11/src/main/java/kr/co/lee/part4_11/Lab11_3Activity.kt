package kr.co.lee.part4_11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.ArrayAdapter
import kr.co.lee.part4_11.databinding.ActivityLab113Binding

class Lab11_3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab113Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLab113Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spinner
        val datas = resources.getStringArray(R.array.spinner_array)
        val aa = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, datas)
        binding.spinner.adapter = aa

        // AutoCompleteTextView
        val autoDatas = resources.getStringArray(R.array.auto_array)
        val autoAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoDatas)
        binding.auto.setAdapter(autoAdapter)

        // Progress
        val thread = ProgressThread()
        thread.start()
    }

    inner class ProgressThread: Thread() {
        override fun run() {
            for(i in 0 until 10) {
                SystemClock.sleep(1000)
                binding.progress.incrementProgressBy(10)
                binding.progress.incrementSecondaryProgressBy(15)
            }
        }
    }
}