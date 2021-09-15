package kr.co.lee.part9_25

// HttpRequester에서 얻은 결과 데이터를 받기 위해 구현되는 인터페이스
interface HttpCallback {
    fun onResult(result: String?)
}