package kr.co.lee.part10_28

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {
    private val QUERY = "travel"
    private val API_KEY = "081fdc41b087400f96ad6961174af090"

    val networkService = RetrofitFactory.create()

    fun getNews(): MutableLiveData<List<ItemModel>> {
        val liveData = MutableLiveData<List<ItemModel>>()
        networkService.getList(QUERY, API_KEY, 1, 10)
            .enqueue(object : Callback<PageListModel> {
                override fun onResponse(
                    call: Call<PageListModel>,
                    response: Response<PageListModel>
                ) {
                    if(response.isSuccessful) {
                        liveData.postValue(response.body()?.articles)
                    }
                }

                override fun onFailure(call: Call<PageListModel>, t: Throwable) {

                }

            })

        return liveData
    }
}