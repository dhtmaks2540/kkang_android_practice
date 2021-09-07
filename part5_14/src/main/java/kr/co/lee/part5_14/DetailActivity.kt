package kr.co.lee.part5_14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import kr.co.lee.part5_14.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    lateinit var adapter: ArrayAdapter<String>
    lateinit var datas: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 자신을 실행시킨 Intent 획득
        val intent = intent
        // MainActivity에서 넘어온 데이터 획득
        val category = intent.getStringExtra("category")

        // 항목 구성
        val helper = DBHelper(this)
        val db = helper.writableDatabase
        val cursor = db.rawQuery("select location from tb_data where category = ?", arrayOf(category))

        datas = ArrayList()
        while(cursor.moveToNext()) {
            datas.add(cursor.getString(0))
        }
        db.close()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, datas)
        binding.detailList.adapter = adapter

        binding.detailList.setOnItemClickListener { parent, view, position, id ->
            val textView = view as TextView
            // 자신을 실행시킨 Intent에 결과 데이터 추가
            val intent = getIntent()
            intent.putExtra("location", textView.text.toString())
            // 업무 수행 결과 추가
            setResult(RESULT_OK, intent)
            // 자신을 종료시키면 자동으로 이전 화면으로 넘어가게 설정
            finish()
        }
    }
}