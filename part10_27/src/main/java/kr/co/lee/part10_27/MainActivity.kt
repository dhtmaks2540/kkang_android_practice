package kr.co.lee.part10_27

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.lee.part10_27.databinding.ActivityMainBinding
import kr.co.lee.part10_27.databinding.ItemMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        @BindingAdapter("bind:publishedAt")
        @JvmStatic
        fun publishedAt(view: TextView, date: String) {
            view.text = AppUtils.getDate(date) + "at" + AppUtils.getTime(date)
        }

        @BindingAdapter("bind:urlToImage")
        @JvmStatic
        fun urlToImage(view: ImageView, url: String) {
            Glide.with(MyApplication.getAppContext()).load(url).override(250, 200).into(view)
        }
    }

    private val QUERY = "travel"
    private val API_KEY = "081fdc41b087400f96ad6961174af090"
    
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // XML을 초기화하고 해당 XML을 이용하기 위한 바인딩 클래스의 객체 반환
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrofit Service 객체 초기화
        val networkService = RetrofitFactory.create()
        
        // Call 객체에게 작업 요청
        networkService.getList(QUERY, API_KEY, 1, 10)
            .enqueue(object : Callback<PageListModel> {
                override fun onResponse(
                    call: Call<PageListModel>,
                    response: Response<PageListModel>
                ) {
                    if(response.isSuccessful) {
                        // 응답이 성공하면 어댑터에 데이터 넣기
                        val adapter = MyAdapter(response.body()!!.articles)
                        binding.recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<PageListModel>, t: Throwable) {

                }

            })
    }

    class ItemViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root) {

    }

    class MyAdapter(val articles: List<ItemModel>): RecyclerView.Adapter<ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val model = articles[position]
            holder.binding.item = model
        }

        override fun getItemCount(): Int {
            return articles.size
        }

    }
}