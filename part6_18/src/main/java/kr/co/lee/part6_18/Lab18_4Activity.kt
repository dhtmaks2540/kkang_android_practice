package kr.co.lee.part6_18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class Lab18_4Activity : AppCompatActivity() {
    /*
    Bottom Sheet는 뷰와 뷰가 상호작용하는 CoordinatorLayout과 잘 어울리는데
    PersistentBottomSheet와 Modal Bottom Sheet 두개가 존재한다
    PersistentBottomSheet는 스크롤에 따라 크기가 커지고 작아지며 계속해서 존재하는 것이고
    Modal Bottom Sheet는 다이얼로그 개념으로 하단에 뜨는것이다
    */

    lateinit var btn: Button
    lateinit var coordinatorLayout: CoordinatorLayout
    private var persistentBottomSheet: BottomSheetBehavior<View>? = null
    private var modalBottomSheet: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab18_4)

        coordinatorLayout = findViewById(R.id.lab4_coordinator)
        btn = findViewById(R.id.lab4_button)
        btn.setOnClickListener {
            createDialog()
        }

        initPersistentBottomSheet()
    }

    private fun createDialog() {
        // 리스트 생성 및 데이터 넣기
        val list = ArrayList<DataVO>()

        var title = "Keep"
        var image = ResourcesCompat.getDrawable(resources, R.drawable.ic_lab4_1, null)!!
        var vo = DataVO(title, image)
        list.add(vo)

        title = "Inbox"
        image = ResourcesCompat.getDrawable(resources, R.drawable.ic_lab4_2, null)!!
        vo = DataVO(title, image)
        list.add(vo)

        title = "Messanger"
        image = ResourcesCompat.getDrawable(resources, R.drawable.ic_lab4_3, null)!!
        vo = DataVO(title, image)
        list.add(vo)

        title = "Google+"
        image = ResourcesCompat.getDrawable(resources, R.drawable.ic_lab4_4, null)!!
        vo = DataVO(title, image)
        list.add(vo)

        // RecyclerView LayoutManager 및 Adapter 적용
        val adapter = Lab4RecyclerViewAdapter(list)
        val view = layoutInflater.inflate(R.layout.lab4_modal_sheet, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.lab4_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Modal Bottom Sheet 생성(BottomSheetDialog 클래스를 사용)
        modalBottomSheet = BottomSheetDialog(this)
        // Bottom Sheet 뷰 지정
        modalBottomSheet?.setContentView(view)
        // Modal Bottom Sheet 보여주기
        modalBottomSheet?.show()
    }

    private fun initPersistentBottomSheet() {
        // Persistent Bottom Sheet 뷰 가져오기
        val bottomSheet = coordinatorLayout.findViewById<View>(R.id.lab4_bottom_sheet)
        // BottomSheetBehavior.from 함수로 지정해주면 bottom sheet가 된다
        persistentBottomSheet = BottomSheetBehavior.from(bottomSheet)
    }
}