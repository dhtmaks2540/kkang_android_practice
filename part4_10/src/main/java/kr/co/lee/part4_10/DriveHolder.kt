package kr.co.lee.part4_10

import android.view.View
import android.widget.ImageView
import android.widget.TextView

class DriveHolder {
    val typeImageView: ImageView
    val titleView: TextView
    val dateView: TextView
    val menuImageView: ImageView

    constructor(root: View) {
        typeImageView = root.findViewById(R.id.custom_item_type_image)
        titleView = root.findViewById(R.id.custom_item_title)
        dateView = root.findViewById(R.id.custom_item_date)
        menuImageView = root.findViewById(R.id.custom_item_menu)
    }
}