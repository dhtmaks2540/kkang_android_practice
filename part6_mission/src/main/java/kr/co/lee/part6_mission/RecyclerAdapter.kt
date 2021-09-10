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

class RecyclerAdapter(val context: Context, val itemList: List<ItemVO>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 항목의 타입이 여러개인 경우 이 메소드를 사용해 타입식별
    override fun getItemViewType(position: Int): Int {
        return itemList[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // getItemViewType을 통해 넘어온 타입을 분기해서 레이아웃 초기화 후 그 뷰를 ViewHolder에 넘겨준다
        if (viewType == ItemVO.TYPE_HEADER) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.mission3_item_header, parent, false)
            return HeaderViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(context).inflate(R.layout.mission3_item_data, parent, false)
            return DataViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 여기서도 타입에 따라서 항목을 따로 만들어준다
        val itemVO = itemList[position]
        if (itemVO.getType() == ItemVO.TYPE_HEADER) {
            // holder 형변환
            val holder = holder as HeaderViewHolder
            // 리스트 아이템 형 변환
            val headerItem = itemVO as HeaderItem
            holder.textView.text = headerItem.headerTitle
        } else {
            val holder = holder as DataViewHolder
            val dataItem = itemVO as DataItem
            holder.nameText.text = dataItem.name
            holder.dateText.text = dataItem.date
            
            // index에 프로필 사진의 색을 다르게 하기 위한 코드
            val count = position % 5
            if (count == 0) {
                (holder.personImage.background as GradientDrawable).setColor(Color.RED)
            } else if (count == 1) {
                (holder.personImage.background as GradientDrawable).setColor(Color.BLUE)
            } else if (count == 2) {
                (holder.personImage.background as GradientDrawable).setColor(Color.MAGENTA)
            } else if (count == 3) {
                (holder.personImage.background as GradientDrawable).setColor(Color.GREEN)
            } else if (count == 4) {
                (holder.personImage.background as GradientDrawable).setColor(Color.YELLOW)
            }
        }
    }

    override fun getItemCount(): Int {
        // 리스트의 개수 넘겨주기
        return itemList.size
    }
}

class RecyclerDecorator(val list: ArrayList<ItemVO>) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        // 리싸이클러뷰에서 아이템의 인덱스를 얻어온다
        val index = parent.getChildAdapterPosition(view)
        // 리스트 아이템 얻어오기
        val itemVO = list[index]
        // 만약 타입이 DataItem이라면
        if (itemVO.getType() == ItemVO.TYPE_DATA) {
            // 아이템의 뷰 배경 색
            view.setBackgroundColor(Color.GRAY)
            // elevation 효과
            ViewCompat.setElevation(view, 20.0f)
        }

        // 사격형으로 아이템 만들기
        outRect.set(20, 20, 20, 20)

    }
}

class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val personImage: ImageView
    val nameText: TextView
    val dateText: TextView

    init {
        personImage = view.findViewById(R.id.mission3_item_person)
        nameText = view.findViewById(R.id.mission3_item_name)
        dateText = view.findViewById(R.id.mission3_item_date)
    }
}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView

    init {
        textView = view.findViewById(R.id.mission3_item_header)
    }
}