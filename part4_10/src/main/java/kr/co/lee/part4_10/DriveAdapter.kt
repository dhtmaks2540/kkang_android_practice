package kr.co.lee.part4_10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat

class DriveAdapter : ArrayAdapter<DriveVO> {
    val adapterContext: Context
    val resId: Int
    val datas: ArrayList<DriveVO>

    constructor(context: Context, resId: Int, datas: ArrayList<DriveVO>): super(context, resId) {
        this.adapterContext = context
        this.resId = resId
        this.datas = datas
    }

    override fun getCount(): Int {
        return datas.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
//        var position = position
//        var parent = parent
        if(convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(resId, null)
            val holder = DriveHolder(convertView)
            convertView.tag = holder
        }
        val holder = convertView?.getTag() as DriveHolder

        val typeImageView = holder.typeImageView
        val titleView = holder.titleView
        val dateView = holder.dateView
        val menuImageView = holder.menuImageView

        val vo = datas[position]

        titleView.text = vo.title
        dateView.text = vo.date

        when(vo.type) {
            "doc" -> {
                typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_type_doc, null))
            }
            "file" -> {
                typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_type_file, null))
            }

            "img" -> {
                typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_type_image, null))
            }
        }

        menuImageView.setOnClickListener {
            val toast = Toast.makeText(context, "${vo.title} menu click", Toast.LENGTH_SHORT)
            toast.show()
        }

        return convertView
    }
}