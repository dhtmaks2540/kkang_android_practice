package kr.co.lee.part6_17

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Lab17_4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab17_4)

        // recyclerView 인스턴스화
        val recyclerView = findViewById<RecyclerView>(R.id.lab3_recycler)
        // 데이터 생성
        val list = ArrayList<String>()
        for(i in 0 until 20) {
            list.add("Item=$i")
        }

        // recyclerView의 강 항목은 LinearLayoutManager를 이용하여 구성(vertical)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // adapter 설정
        recyclerView.adapter = MyAdapter(list)
        // recyclerView Decoration 설정
        recyclerView.addItemDecoration(MyItemDecoration())
    }

    private class MyAdapter: RecyclerView.Adapter<MyViewHolder> {
        private lateinit var list: List<String>

        constructor(list: List<String>): super() {
            this.list = list
        }

        // 항목을 구성하기 위한 layout_xml 파일 inflate
        // 리턴으로는 view를 findViewByIf 할 ViewHolder 리턴
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).
                    inflate(android.R.layout.simple_list_item_1, parent, false)
            return MyViewHolder(view)
        }

        // 각 항목을 구성하기 위해 호출
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val text = list[position]
            holder.title.text = text
        }

        // 항목 개수
        override fun getItemCount(): Int = list.size

    }

    // findViewById의 성능이슈 해결을 위해수 adapter 내부에서 메모리에 유지되게끔 설정
    private class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(android.R.id.text1)
    }

    // RecyclerView의 강 항목을 꾸미기 위한 클래스
    inner class MyItemDecoration: RecyclerView.ItemDecoration() {
        // 각 항목을 배치할 때 호출
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            // 항목의 인덱스
            val index = parent.getChildAdapterPosition(view) + 1
            // 3번째 마다 세로 여백 60
            if(index % 3 == 0)
                outRect.set(20, 20, 20, 60)
            // 나머지는 여백 20
            else
                outRect.set(20, 20, 20, 20)
            view.setBackgroundColor(Color.LTGRAY)
            ViewCompat.setElevation(view, 20.0f)
        }
        
        // 항목이 모두 추가된 후
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)
            val width = parent.width
            val height = parent.height
            val dr = ResourcesCompat.getDrawable(resources, R.drawable.android, null)
            val drWidth = dr?.intrinsicWidth
            val drHeight = dr?.intrinsicHeight
            val left = width / 2 - drWidth!! / 2
            val top = height / 2 - drHeight!! / 2
            // 가운데 이미지 그리기
            c.drawBitmap(BitmapFactory.decodeResource(resources, R.drawable.android), left.toFloat(), top.toFloat(), null)
        }
    }
}