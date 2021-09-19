package kr.co.lee.part10_29.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.co.lee.part10_29.model.ItemModel

@Dao
interface ArticleDAO {
    @Query("SELECT * FROM article")
    fun getAll(): List<ItemModel>

    @Insert
    fun insertAll(vararg users: ItemModel)

    @Query("DELETE FROM article")
    fun deleteAll()
}