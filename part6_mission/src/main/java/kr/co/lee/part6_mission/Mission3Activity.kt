package kr.co.lee.part6_mission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Mission3Activity : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    lateinit var list: ArrayList<ItemVO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission3)

        recycler = findViewById(R.id.mission3_recycler)
        
        // SQLiteOpenHelper를 구현한 클래스 인스턴스 생성
        val helpder = DBHelper(this)
        val db = helpder.writableDatabase

        // 날짜에 따라 cursor객체 만들기
        val todayCursor = db.rawQuery("select * from tb_data where date='2017-07-01'", null)
        val yesterdayCursor = db.rawQuery("select * from tb_data where date='2017-06-30'", null)
        val beforeCursor = db.rawQuery("select * from tb_data where date != '2017-07-01' and date != '2017-06-30'", null)

        list = ArrayList()
        
        // row가 있다면(cursor가 있다면)
        if(todayCursor.count != 0) {
            val item = HeaderItem("오늘")
            list.add(item)
            // todayCursor의 값이 있는지 확인하며 데이터 넣기
            while(todayCursor.moveToNext()) {
                val name = todayCursor.getString(1)
                val date = todayCursor.getString(2)
                val dataItem = DataItem(name, date)
                list.add(dataItem)
            }
        }

        if(yesterdayCursor.count != 0) {
            val item = HeaderItem("어제")
            list.add(item)
            while(yesterdayCursor.moveToNext()) {
                val name = yesterdayCursor.getString(1)
                val date = yesterdayCursor.getString(2)
                val dataItem = DataItem(name, date)
                list.add(dataItem)
            }
        }

        if(beforeCursor.count != 0) {
            val item = HeaderItem("이전")
            list.add(item)
            while(beforeCursor.moveToNext()) {
                val name = beforeCursor.getString(1)
                val date = beforeCursor.getString(2)
                val dataItem = DataItem(name, date)
                list.add(dataItem)
            }
        }

        // adapter, layoutManager, ItemDecoration 넣기
        recycler.adapter = RecyclerAdapter(this, list)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(RecyclerDecorator(list))
    }
}