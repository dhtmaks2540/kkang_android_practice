package kr.co.lee.part10_30.datasource

import androidx.paging.PageKeyedDataSource
import kr.co.lee.part10_30.MyApplication
import kr.co.lee.part10_30.model.ItemModel

class MyDataSource: PageKeyedDataSource<Long, ItemModel>() {
    private val QUERY = "travel"
    private val API_KEY = "081fdc41b087400f96ad6961174af090"

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, ItemModel>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ItemModel>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ItemModel>
    ) {
        TODO("Not yet implemented")
    }

    class InsertDataThread(val articles: List<ItemModel>): Thread() {
        override fun run() {

        }
    }
}