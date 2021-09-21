package kr.co.lee.part10_30

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kr.co.lee.part10_30.datasource.MyDataFactory
import kr.co.lee.part10_30.model.ItemModel

class MyViewModel : ViewModel() {
    var itemLiveData: LiveData<PagedList<ItemModel>>? = null
    var pagedListConfig: PagedList.Config? = null

    init {
        // PagedList 설정 객체
        pagedListConfig = (PagedList.Config.Builder())
            .setInitialLoadSizeHint(3) // 초기 획득 항목 수
            .setPageSize(3) // 그 다음 페이지부터 획득하는 항목 수
            .build()
    }

    fun getNews(): LiveData<PagedList<ItemModel>> {
        val connectivityManager = MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if(networkInfo != null) {
            // DataSource.Factory 상속 클래스 -> DataSource를 생성해주는 역할
            // DataSource.Factory와 PagedList.Config 객체를 Builder에 등록
            val dataFactory = MyDataFactory()
            itemLiveData = LivePagedListBuilder(dataFactory, pagedListConfig!!).build()
            return itemLiveData!!
        } else {
            val factory = MyApplication.dao.getAll()
            itemLiveData = LivePagedListBuilder(factory, pagedListConfig!!).build()
            return itemLiveData!!
        }
    }
}