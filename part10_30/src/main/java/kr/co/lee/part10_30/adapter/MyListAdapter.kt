package kr.co.lee.part10_30.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.part10_30.databinding.ItemMainBinding
import kr.co.lee.part10_30.model.ItemModel

class MyListAdapter(val context: Context) :
    PagedListAdapter<ItemModel, MyListAdapter.ItemViewHolder>(
        DIFF_CALLBACK
    ) {
    companion object {
        // RecyclerView의 성능 개선을 위해 사용함
        // 기존에 출력된 항목과 새로운 항목을 비교해서 같은 데이터인지를 판단하는 역할
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ItemViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val article = getItem(position)
        holder.binding.data = article
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemMainBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }
}