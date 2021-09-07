package kr.co.lee.part3_mission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.lee.part3_mission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            if (binding.editName.text.toString() == "" || binding.editName.length() == 0) {
                showMessage("이름이 등록되지 않았습니다.")
            } else {
                val dbHelper = DBHelper(this)
                val db = dbHelper.writableDatabase
                db.execSQL(
                    "insert into tb_contact (name, phone, email) values(?,?,?)",
                    arrayOf(
                        binding.editName.text.toString(),
                        binding.editPhone.text.toString(),
                        binding.editEmail.text.toString()
                    )
                )
                db.close()

                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showMessage(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}