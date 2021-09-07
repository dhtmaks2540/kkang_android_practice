package kr.co.lee.part4_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kr.co.lee.part4_10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var arrayDatas: Array<String> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arrayDatas = resources.getStringArray(R.array.location)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayDatas)
        binding.mainListviewArray.adapter = arrayAdapter
        binding.mainListviewArray.setOnItemClickListener { parent, view, position, id ->
            val toast = Toast.makeText(this, arrayDatas[position], Toast.LENGTH_SHORT)
            toast.show()
        }

        val simpleDatas = ArrayList<HashMap<String, String>>()
        val helper = DBHelper(this)
        val db = helper.writableDatabase
        var cursor = db.rawQuery("select * from tb_data", null)
        while (cursor.moveToNext()) {
            val map = HashMap<String, String>()
            map.put("name", cursor.getString(1))
            map.put("content", cursor.getString(2))
            simpleDatas.add(map)
        }

        val adapter = SimpleAdapter(
            this,
            simpleDatas,
            android.R.layout.simple_list_item_2,
            arrayOf("name", "content"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.mainListviewSimple.adapter = adapter

        val cursorAdapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            cursor,
            arrayOf("name", "content"),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        binding.mainListviewCursor.adapter = cursorAdapter
    }
}