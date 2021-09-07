package kr.co.lee.part3_8

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.lee.part3_8.databinding.ActivityReadDbBinding

class ReadDBActivity: AppCompatActivity() {

    lateinit var binding: ActivityReadDbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReadDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val helper = DBHelper(this)
        val db = helper.writableDatabase
        val cursor = db.rawQuery("select title, content from tb_memo order by _id desc limit 1", null)

        while(cursor.moveToNext()) {
            binding.readTitle.text = cursor.getString(0)
            binding.readContent.text = cursor.getString(1)
        }
    }
}