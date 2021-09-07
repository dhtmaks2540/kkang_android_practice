package kr.co.lee.part4_mission1

import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CallLogHolder {
    val profileImageView: ImageView
    val nameView: TextView
    val dateView: TextView
    val callView: ImageView

    constructor(root: View) {
        profileImageView = root.findViewById(R.id.item_profile_img)
        nameView = root.findViewById(R.id.item_name)
        dateView = root.findViewById(R.id.item_date)
        callView = root.findViewById(R.id.item_call_img)
    }
}