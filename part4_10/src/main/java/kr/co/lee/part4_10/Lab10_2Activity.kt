package kr.co.lee.part4_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lee.part4_10.databinding.ActivityLab102Binding

class Lab10_2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab102Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLab102Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val helper = DBHelper(this)
        val db = helper.writableDatabase
        val cursor = db.rawQuery("select * from tb_drive", null)

        val datas = ArrayList<DriveVO>()
        while(cursor.moveToNext()) {
            val vo = DriveVO(cursor.getString(3), cursor.getString(1), cursor.getString(2))
            datas.add(vo)
        }

        db.close()

        val adapter = DriveAdapter(this, R.layout.custom_item, datas)
        binding.customListview.adapter = adapter
    }
}