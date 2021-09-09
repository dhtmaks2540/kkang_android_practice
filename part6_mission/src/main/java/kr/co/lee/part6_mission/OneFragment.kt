package kr.co.lee.part6_mission

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OneFragment: Fragment() {

    lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<String>()
        for(i in 0 until 20) {
            list.add("Item=$i")
        }

        recycler = view.findViewById(R.id.one_recycler)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = MyAdapter(list)
        recycler.addItemDecoration(RecyclerDecoration())
    }

    class MyAdapter(val list: List<String>): RecyclerView.Adapter<ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val text = list[position]
            holder.textView.text = text
        }

        override fun getItemCount(): Int {
            return list.size
        }

    }

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(android.R.id.text1)
        }
    }

    class RecyclerDecoration: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            outRect.set(20, 20, 20, 20)
            view.setBackgroundColor(Color.LTGRAY)
            ViewCompat.setElevation(view, 20.0f)
        }

    }
}