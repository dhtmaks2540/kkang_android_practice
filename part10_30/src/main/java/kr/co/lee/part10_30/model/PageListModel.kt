package kr.co.lee.part10_30.model

data class PageListModel(val id: Long, val totalResults: Long, val articles: List<ItemModel>)
