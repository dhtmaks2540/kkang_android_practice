package kr.co.lee.part3_mission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lee.part3_mission.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase
        var cursor =
            db.rawQuery("select name, phone, email from tb_contact order by _id desc limit 1", null)

        while (cursor.moveToNext()) {
            binding.textName.text = ": ${cursor.getString(0)}"
            binding.textPhone.text = ": ${cursor.getString(1)}"
            binding.textEmail.text = ": ${cursor.getString(2)}"
        }

        db.close()
    }
}