package kr.co.lee.part6_mission

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val context: Context, val itemList: List<ItemVO>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemViewType(position: Int): Int {
        return itemList[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ItemVO.TYPE_HEADER) {
            val view = LayoutInflater.from(context).inflate(R.layout.mission3_item_header, parent, false)
            return HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.mission3_item_data, parent, false)
            return DataViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemVO = itemList[position]
        if(itemVO.getType() == ItemVO.TYPE_HEADER) {
            val holder = holder as HeaderViewHolder
            val headerItem = itemVO as HeaderItem

            if(headerItem.headerTitle == "2017-07-01") {
                holder.textView.text = "오늘"
            } else if(headerItem.headerTitle == "2017-06-30") {
                holder.textView.text = "어제"
            } else {
                holder.textView.text = "이전"
            }
        } else {
            val holder = holder as DataViewHolder
            val dataItem = itemVO as DataItem
            holder.nameText.text = dataItem.name
            holder.dateText.text = dataItem.date
            (holder.personImage.background as GradientDrawable).setColor(Color.parseColor("0xFF009688"))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

class DataViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val personImage: ImageView
    val nameText: TextView
    val dateText: TextView
    val callImage: ImageView

    init {
        personImage = view.findViewById(R.id.mission3_item_person)
        nameText = view.findViewById(R.id.mission3_item_name)
        dateText = view.findViewById(R.id.mission3_item_date)
        callImage = view.findViewById(R.id.mission3_item_dialer)
    }
}

class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textView: TextView

    init {
        textView = view.findViewById(R.id.mission3_item_header)
    }
}

class RecyclerDecorator: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.set(20, 20, 20, 20)
        view.setBackgroundColor(Color.GRAY)
        ViewCompat.setElevation(view, 20.0f)
    }
}