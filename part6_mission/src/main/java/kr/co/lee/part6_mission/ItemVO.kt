package kr.co.lee.part6_mission

abstract class ItemVO {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_DATA = 1
    }
    abstract fun getType(): Int
}

data class HeaderItem(val headerTitle: String): ItemVO() {
    override fun getType(): Int {
        return ItemVO.TYPE_HEADER
    }
}

data class DataItem(val name: String, val date: String): ItemVO() {
    override fun getType(): Int {
        return ItemVO.TYPE_DATA
    }
}