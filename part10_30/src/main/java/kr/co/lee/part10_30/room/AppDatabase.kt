package kr.co.lee.part10_30.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.co.lee.part10_30.model.ItemModel

@Database(entities = [ItemModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDAO
}