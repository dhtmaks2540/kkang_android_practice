package kr.co.lee.part5_14

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.lee.part5_14.databinding.ActivityMainBinding

class MainActivity() : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<String>
    lateinit var datas: ArrayList<String>
    var category: String = ""
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 항목 구성 데이터 획득
        val helper = DBHelper(this)
        val db = helper.writableDatabase
        val cursor = db.rawQuery("select location from tb_data where category='0'", null)
        datas = ArrayList()
        while (cursor.moveToNext()) {
            datas.add(cursor.getString(0))
        }
        db.close()
        // Adapter 정용으로 ListView 구성
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas)
        binding.mainList.adapter = adapter

//        // 액티비티 콜백 함수
//        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult>() {
//            onActivityResult()
//        })

        // 항목 선택 이벤트
        binding.mainList.setOnItemClickListener { parent, view, position, id ->
            val textView = view as TextView
            category = textView.text.toString()
            // Extra 데이터를 넘기면서 Intent 발생
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("category", category)
            startActivityForResult(intent, 10)
        }
    }

    // 화면이 되돌아 왔을 때 자동 호출
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            // DetailActivity가 넘긴 데이터 획득
            val location = data?.getStringExtra("location")
            val toast = Toast.makeText(this, "$category : $location", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}