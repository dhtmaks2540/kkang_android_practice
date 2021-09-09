package kr.co.lee.part6_mission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Mission3Activity : AppCompatActivity() {

    lateinit var recycler: RecyclerView

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

        val testList: ArrayList<ItemVO> = ArrayList()

        while(todayCursor.moveToNext()) {
            val headerVO = HeaderItem(todayCursor.getString(2))
            val dataVO = DataItem(todayCursor.getString(1), todayCursor.getString(2))
            testList.add(dataVO)
            testList.add(headerVO)
        }

        while(yesterdayCursor.moveToNext()) {
            val headerVO = HeaderItem(yesterdayCursor.getString(2))
            val dataVO = DataItem(yesterdayCursor.getString(1), yesterdayCursor.getString(2))
            testList.add(dataVO)
            testList.add(headerVO)
        }

        while(beforeCursor.moveToNext()) {
            val headerVO = HeaderItem(beforeCursor.getString(2))
            val dataVO = DataItem(beforeCursor.getString(1), beforeCursor.getString(2))
            testList.add(dataVO)
            testList.add(headerVO)
        }

        recycler.adapter = RecyclerAdapter(this, testList)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(RecyclerDecorator())
    }
}