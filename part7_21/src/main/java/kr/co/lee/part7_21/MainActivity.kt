package kr.co.lee.part7_21

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    lateinit var listView: ListView
    lateinit var nameView: EditText
    lateinit var phoneView: EditText
    lateinit var btn: Button

    var isUpdate = false
    var _id: String = ""

    var datas: ArrayList<HashMap<String, String>>? = null

    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lab1_listview)
        nameView = findViewById(R.id.lab1_name)
        phoneView = findViewById(R.id.lab1_phone)
        btn = findViewById(R.id.lab1_btn)

        listView.setOnItemClickListener(this)
        listView.setOnItemLongClickListener(this)
        btn.setOnClickListener {
            // 아이템이 클릭되어 isUpdate가 True일 때
            if(isUpdate) {
                // update 구문
                val name = nameView.text.toString()
                val phone = phoneView.text.toString()
                if(name != "" && phone != "") {
                    // Content Resolver에서 값을 처리하기 위해 사용되는 클래스
                    val values = ContentValues()
                    // 값 저장
                    values.put("name", name)
                    values.put("phone", phone)
                    contentResolver.update(uri, values, "_id=?", arrayOf(_id))
                    setAdapter()
                }
                nameView.setText("")
                phoneView.setText("")
                isUpdate = false
                // 저장 모드가 아니라면(아이템이 클릭되지 않았다면)
            } else {
                // insert 구문
                val name = nameView.text.toString()
                val phone = phoneView.text.toString()
                if(name != "" && phone != "") {
                    val values = ContentValues()
                    values.put("name", name)
                    values.put("phone", phone)
                    contentResolver.insert(uri, values)
                    setAdapter()
                }
                nameView.setText("")
                phoneView.setText("")
            }
        }

        uri = Uri.parse("content://kr.co.lee.part.Provider")
        setAdapter()
    }

    private fun setAdapter() {
        datas = ArrayList()
        // getContentResolver를 사용해  ContentResolver 객체 획득
        // Uri 값을 첫 번째 매개변수로 주어 어느 콘텐트 프로바이더를 사용할 것인지
        val cursor = contentResolver.query(uri, null, null, null, null)
        while(cursor?.moveToNext()!!) {
            val map = HashMap<String, String>()
            map["id"] = cursor.getString(0)
            map["name"] = cursor.getString(1)
            map["phone"] = cursor.getString(2)
            datas?.add(map)
        }

        val adapter = SimpleAdapter(this, datas, android.R.layout.simple_list_item_2,
            arrayOf("name", "phone"), intArrayOf(android.R.id.text1, android.R.id.text2))

        listView.adapter = adapter
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // 인덱스값을 이용해 리스트에서 값을 얻어온 후 텍스트뷰와 변수에 대입
        val map = datas?.get(position)
        nameView.setText(map?.get("name"))
        phoneView.setText(map?.get("phone"))
        _id = map?.get("id")!!
        isUpdate = true
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        // 리스트로부터 map 객체를 얻어온 후 uri를 사용해 삭제
        val map = datas?.get(position)
        contentResolver.delete(uri, "_id=?", arrayOf(map?.get("id")))
        setAdapter()
        return false
    }
}