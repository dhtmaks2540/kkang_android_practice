package kr.co.lee.part4_mission1

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import kr.co.lee.part4_mission1.databinding.ActivityMission3Binding

class Mission3Activity : AppCompatActivity(), AdapterView.OnItemClickListener, TextWatcher {

    private lateinit var binding: ActivityMission3Binding
    var base: Array<String> = arrayOf()
    var datas: ArrayList<SpannableStringBuilder> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMission3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // StringArray 얻어오기
        base = resources.getStringArray(R.array.search_array)
        
        // 리스너 연결
        binding.mission3List.onItemClickListener = this
        binding.edit.addTextChangedListener(this)

        // adapter 갱신
        val adapter = ArrayAdapter<SpannableStringBuilder>(this, android.R.layout.simple_list_item_1, datas)
        binding.mission3List.adapter = adapter
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // ListView 아이템 값 editText에 셋팅
        binding.edit.setText(datas[position].toString())

        datas = ArrayList()

        // adapter 갱신
        val adapter = ArrayAdapter<SpannableStringBuilder>(this, android.R.layout.simple_list_item_1, datas)
        binding.mission3List.adapter = adapter
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        datas = ArrayList()
        // String 배열 for문 돌리면서
        for(value in base) {
            // 값에 바뀐 값이 있다면
            if(value.contains(s!!)) {
                // 시작위치, 끝위치 얻어오기
                val start = value.indexOf(s.toString())
                val end = start + s.length

                // value를 가지고 SpannableStringBuilder 객체 생성
                val builder = SpannableStringBuilder(value)
                // 색 넣기
                builder.setSpan(ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                // 굵게 쓰기 넣기
                builder.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                // SpannableStringBuilder ArrayList에 꾸민 값 넣기
                datas.add(builder)
            }
        }

        // 수정된 datas를 가지고 adapter 갱싱
        val adapter = ArrayAdapter<SpannableStringBuilder>(this, android.R.layout.simple_list_item_1, datas)
        binding.mission3List.adapter = adapter

    }

    override fun afterTextChanged(s: Editable?) {

    }
}