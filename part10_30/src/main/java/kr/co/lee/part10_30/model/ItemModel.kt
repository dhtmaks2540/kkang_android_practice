package kr.co.lee.part10_30.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ItemModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val author: String,
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: String
)

//@Entity(tableName = "article")
//class ItemModel {
//    @PrimaryKey(autoGenerate = true)
//    var id: Long = 0
//    var author: String? = null
//    var title: String? = null
//    var description: String? = null
//    var urlToImage: String? = null
//    var publishedAt: String? = null
//}

