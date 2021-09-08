package kr.co.lee.part6_17

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.ListFragment

// ListFragment를 상속 받아서 사용(ListView가 내장되어 있기 때문에 onCreateView에서 레이아웃 지정할 필요가 없다)
class OneFragment: ListFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datas = arrayOf("박찬호", "류현진", "김현수", "오승롼")
        val aa = ArrayAdapter<String>(activity?.baseContext!!, android.R.layout.simple_list_item_1, datas)
        listAdapter = aa
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val toast = Toast.makeText(activity?.baseContext!!, l.adapter.getItem(position).toString(), Toast.LENGTH_SHORT)
        toast.show()
    }
}