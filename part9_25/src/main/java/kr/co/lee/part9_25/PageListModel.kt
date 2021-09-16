package kr.co.lee.part9_25

// VO 클래스 선언
data class PageListModel(val id: Long, val totalResults: Long, val articles: List<ItemModel>) {
}