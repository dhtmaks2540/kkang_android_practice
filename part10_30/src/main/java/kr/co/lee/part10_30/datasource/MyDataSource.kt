package kr.co.lee.part10_30.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.google.gson.Gson
import kr.co.lee.part10_30.MyApplication
import kr.co.lee.part10_30.model.ItemModel
import kr.co.lee.part10_30.model.PageListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// DataSource: 실제 데이터를 획득하는 곳
class MyDataSource: PageKeyedDataSource<Long, ItemModel>() {
    private val QUERY = "travel"
    private val API_KEY = "081fdc41b087400f96ad6961174af090"

    // 페이지 데이터 획득을 위해 반복 호출
    // param 정보에 그 다음 페이지 번호가 전달
    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, ItemModel>) {
        MyApplication.networkService.getList(QUERY, API_KEY, params.key, params.requestedLoadSize).enqueue(object: Callback<PageListModel> {
            override fun onResponse(call: Call<PageListModel>, response: Response<PageListModel>) {
                if(response.isSuccessful) {
                    val nextKey = if(params.key == response.body()?.totalResults) null else params.key + 1
                    callback.onResult(response.body()?.articles!!, nextKey)
                }
            }

            override fun onFailure(call: Call<PageListModel>, t: Throwable) {
            }

        })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ItemModel>) {
    }

    // 초기 데이터 획득
    // 매개변수로 전달되는 LoadInitialParams에 담긴 Config정보를 활용해 초기 데이터 획득 작업을 진행
    // 획득한 데이터는 LoadInitialCallback에 담아주면 된다
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ItemModel>
    ) {
        MyApplication.networkService.getList(QUERY, API_KEY, 1, params.requestedLoadSize)
            .enqueue(object : Callback<PageListModel> {
                override fun onResponse(
                    call: Call<PageListModel>,
                    response: Response<PageListModel>
                ) {
                    if(response.isSuccessful) {
//                        Log.d("Lee Chang Hee", Gson().toJson(response.raw()))
                        Log.d("Lee Chang Hee", Gson().toJson(response.body()))
                        // 획득한 데이터 callback으로 전달
                        callback.onResult(response.body()?.articles!!, null, 2L)

                        InsertDataThread(response.body()?.articles!!).start()
                    }
                }

                override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                }

            })
    }

    class InsertDataThread(val articles: List<ItemModel>): Thread() {
        override fun run() {
            MyApplication.dao.deleteAll()
            MyApplication.dao.insertAll(*articles.toTypedArray())
        }
    }
}