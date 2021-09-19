package kr.co.lee.part10_29

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kr.co.lee.part10_29.model.ItemModel
import kr.co.lee.part10_29.model.PageListModel
import kr.co.lee.part10_29.retrofit.RetrofitFactory
import kr.co.lee.part10_29.room.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {
    private val QUERY = "travel"
    private val API_KEY = "081fdc41b087400f96ad6961174af090"

    val networkService = RetrofitFactory.create()

    val db = Room.databaseBuilder(MyApplication.getAppContext()!!, AppDatabase::class.java, "database-name").build()
    val dao = db.articleDao()

    fun getNews(): MutableLiveData<List<ItemModel>> {
        // network 상태 파악
        val connectivityManager = MyApplication.getAppContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        // 네트워크가 있다면
        if(networkInfo != null) {
            return getNewsFromNetwork()
        // 네트워크가 없다면
        } else {
            val liveData = MutableLiveData<List<ItemModel>>()
            GetAllThread(liveData).start()
            return liveData
        }
    }

    private fun getNewsFromNetwork(): MutableLiveData<List<ItemModel>> {
        Log.d("LeeChangHee", "getNewsFromNetwork...")
        val liveData = MutableLiveData<List<ItemModel>>()
        networkService.getList(QUERY, API_KEY, 1, 10)
            .enqueue(object: Callback<PageListModel> {
                override fun onResponse(
                    call: Call<PageListModel>,
                    response: Response<PageListModel>
                ) {
                    // 응답이 성공했다면
                    if(response.isSuccessful) {
                        // 라이브데이터를 이용하며 액티비티에 데이터 보내기
                        liveData.postValue(response.body()?.articles)

                        // 쓰레드를 이용하여 DB에 데이터 넣기
                        InsertDataThread(response.body()?.articles!!).start()
                    }
                }

                override fun onFailure(call: Call<PageListModel>, t: Throwable) {

                }

            })
        return liveData
    }

    // 룸을 이용한 모든 DB 데이터 획득
    inner class GetAllThread(val liveData: MutableLiveData<List<ItemModel>>): Thread() {
        override fun run() {
            Log.d("Lee Change Hee", "db select...")
            val daoData = dao.getAll()
            liveData.postValue(daoData)
        }
    }

    // 룸을 이용한 테이블에 데이터 삭제 및 추가
    inner class InsertDataThread(val articles: List<ItemModel>): Thread() {
        override fun run() {
            Log.d("Lee Chang Hee", "db delete... insert...")
            dao.deleteAll()
            dao.insertAll(*articles.toTypedArray())
        }
    }
}