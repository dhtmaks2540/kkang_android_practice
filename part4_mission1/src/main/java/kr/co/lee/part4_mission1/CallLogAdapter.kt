package kr.co.lee.part4_mission1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat

class CallLogAdapter : ArrayAdapter<CallLogVO> {
    val adapterContext: Context
    val datas: ArrayList<CallLogVO>
    val resId: Int

    constructor(context: Context, resId: Int, datas: ArrayList<CallLogVO>): super(context, resId) {
        this.adapterContext = context
        this.datas = datas
        this.resId = resId
    }

    // 아이템 개수 넘기기
    override fun getCount(): Int {
        return datas.size
    }

    // 한 항목을 굿ㅇ하기 위해 자동 호출
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // 레이아웃 초기화 여부
        if(convertView == null) { // 한적 없다면
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(resId, null)
            val holder = CallLogHolder(convertView) // CallLogHolder 클래스를 이용해 Adapter에 의해 메모리에 지속된다면 findViewById는 뷰당 한번만 호출
            convertView.setTag(holder) // setTag 함수를 이용해서 holder 저장
        }

        val holder = convertView?.getTag() as CallLogHolder // getTag 함수를 이용해서 holder 불러오기

        val profileImageView = holder.profileImageView
        val nameView = holder.nameView
        val dateView = holder.dateView
        val callView = holder.callView

        val vo = datas[position]

        nameView.text = vo.name
        dateView.text = vo.date

        if(vo.imageType == "yes") {
            profileImageView.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.hong, null))
        } else {
            profileImageView.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_person, null))
        }

        if(vo.phone != "") {
            callView.setOnClickListener {
                // 전화 걸기 Intent(퍼미션 여부 확인은 어떻게..?
                val intent = Intent()
                intent.setAction(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:${vo.phone}"))
                context.startActivity(intent)
            }
        }
        
        return convertView
    }
}