package kr.co.lee.part9_25

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Lab25_3Activity : AppCompatActivity() {

    private val QUERY = "travel"
    private val API_KEY = "081fdc41b087400f96ad6961174af090"

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab25_3)

        recyclerView = findViewById(R.id.lab3_list)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Service 객체 획득
        val networkService = RetrofitFactory.create()
        // Service 객체를 이용해 인터페이스에서 정의한 함수를 호출해 Call 객체 반환
        networkService.getList(QUERY, API_KEY, 1, 10)
            // enqueue 함수를 사용해 Call 객체에게 일을 시킨다
            // Callback 클래스의 객체를 매개변수로 지정하면 네트워킹 시도
            .enqueue(object: Callback<PageListModel> {
                // 정상적으로 결과 받음
                override fun onResponse(
                    call: Call<PageListModel>,
                    response: Response<PageListModel>
                ) {
                    if(response.isSuccessful) {
                        val adapter = MyAdapter(response.body()?.articles!!)
                        recyclerView.adapter = adapter
                    }
                }

                // 서버 연동에 실패
                override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                }

            })
    }

    // RecyclerView에 사용할 Holder
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var itemTitleView: TextView = view.findViewById(R.id.lab3_item_title)
        var itemTimeView: TextView = view.findViewById(R.id.lab3_item_time)
        var itemDescView: TextView = view.findViewById(R.id.lab3_item_desc)
        var itemImageView: ImageView = view.findViewById(R.id.lab3_item_image)
    }

    inner class MyAdapter(val articles: List<ItemModel>): RecyclerView.Adapter<ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_lab3, parent, false)

            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = articles[position]

            val author = item.author
            val titleString = author + "-" + item.title

            holder.itemTitleView.text = titleString
            holder.itemTimeView.text = ".."
            holder.itemDescView.text = item.description
            Glide.with(this@Lab25_3Activity).load(item.urlToImage).override(250, 200).into(holder.itemImageView)

        }

        override fun getItemCount(): Int {
            return articles.size
        }

    }
}