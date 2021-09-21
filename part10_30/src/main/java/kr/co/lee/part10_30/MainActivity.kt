package kr.co.lee.part10_30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kr.co.lee.part10_30.adapter.MyListAdapter
import kr.co.lee.part10_30.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        // Layout에서 Bind로 설정된 속성 BindingAdapter 어노테이션을 이용해 설정
        @JvmStatic
        @BindingAdapter("bind:publishedAt")
        fun publishedAt(view: TextView, date: String) {
            view.text = AppUtils.getDate(date) + " at " + AppUtils.getTime(date)
        }

        @JvmStatic
        @BindingAdapter("bind:urlToImage")
        fun urlToImage(view: ImageView, url: String) {
            Glide.with(MyApplication.getAppContext()!!).load(url).override(250, 200).into(view)
        }
    }

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyListAdapter(applicationContext)

        // ViewModel 생성
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getNews().observe(this, {
            // submitList 함수를 이용해 adapter에 데이터 전달
            adapter.submitList(it)
        })
        binding.recyclerView.adapter = adapter
    }
}