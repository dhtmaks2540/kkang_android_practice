package kr.co.lee.part9_mission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val list: ArrayList<WeatherVO>): RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)

        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val weather = list[position]

//         Url 받아서 처리
//        holder.weatherImage.
        holder.weatherText.text = "${weather.max}:${weather.min}"
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val weatherImage: ImageView = view.findViewById(R.id.item_image1)
    val weatherText: TextView = view.findViewById(R.id.item_temperature)
}