package kr.co.lee.part3_9

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kr.co.lee.part3_9.databinding.ActivityReadFileBinding
import java.io.*
import java.lang.Exception

class ReadFileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReadFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val file = File(Environment.getExternalStorageDirectory().absolutePath + "/myApp/myfile.txt")

        try {
            val reader = BufferedReader(FileReader(file))

            val buffer = StringBuffer()
            var line: String

            reader.readLines().forEach {
                buffer.append(it)
            }

            binding.fileResult.text = buffer.toString()
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}