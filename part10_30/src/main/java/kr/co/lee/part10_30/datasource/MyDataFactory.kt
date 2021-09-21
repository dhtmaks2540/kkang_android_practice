package kr.co.lee.part10_30.datasource

import androidx.paging.DataSource
import kr.co.lee.part10_30.model.ItemModel

// DataSource 객체를 만들어주는 역할
class MyDataFactory: DataSource.Factory<Long, ItemModel>() {
    // create 함수에서 준비된 DataSource 객체를 생성해 반환
    override fun create(): DataSource<Long, ItemModel> {
        return MyDataSource()
    }
}