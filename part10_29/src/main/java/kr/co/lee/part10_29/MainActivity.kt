package kr.co.lee.part10_29

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.lee.part10_29.databinding.ActivityMainBinding
import kr.co.lee.part10_29.databinding.ItemMainBinding
import kr.co.lee.part10_29.model.ItemModel

class MainActivity : AppCompatActivity() {
    companion object {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getNews().observe(this, {
            val adapter = MyAdapter(it)
            binding.recyclerView.adapter = adapter
        })

    }

    class ItemViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

    class MyAdapter(val articles: List<ItemModel>): RecyclerView.Adapter<ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val binding= ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val model = articles[position]
            holder.binding.data = model
        }

        override fun getItemCount(): Int {
            return articles.size
        }

    }
}