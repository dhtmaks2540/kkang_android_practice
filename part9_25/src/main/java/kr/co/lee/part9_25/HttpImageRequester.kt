package kr.co.lee.part9_25

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.Exception

class HttpImageRequester {

    var http: HttpImageTask? = null

    fun request(url: String, param: HashMap<String, String>?, callback: HttpImageCallback) {
        http = HttpImageTask(url, param, callback)
        http?.execute()
    }

    fun cancel() {
        if(http != null) {
            http?.cancel(true)
        }
    }


    class HttpImageTask(val url: String, val param: HashMap<String,String>?, val callback: HttpImageCallback):
        AsyncTask<Void, Void, Bitmap>() {

        override fun doInBackground(vararg params: Void?): Bitmap {
            var response: Bitmap? = null
            var postData: String = ""
            var pw: PrintWriter? = null

            try {
                // URL 클래스는 서버의 URL 정보를 표현
                val text = URL(url)
                // HttpURLConnection은 실제 HTTP 연결을 요청하는 클래스
                val http = text.openConnection() as HttpURLConnection
                // setter 함수들을 이용하여 설정
                http.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=UTF-8")
                http.connectTimeout = 10000
                http.readTimeout = 10000
                http.requestMethod = "POST"
                http.doInput = true
                http.doOutput = true

                if(param != null && param.size > 0) {
                    val entries: Iterator<Map.Entry<String, String>> = param.entries.iterator()
                    var index = 0
                    while(entries.hasNext()) {
                        if (index != 0) {
                            postData = "$postData&"
                        }
                        val mapEntry = entries.next() as Map.Entry<String, String>
                        postData = postData + mapEntry.key + "=" + URLEncoder.encode(
                            mapEntry.value,
                            "UTF-8"
                        )
                        index++
                    }
                    // post 방식으로 데이터 전송
                    pw = PrintWriter(OutputStreamWriter(http.outputStream, "UTF-8"))
                    pw.write(postData)
                    pw.flush()
                }
                response = BitmapFactory.decodeStream(http.inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if(pw != null) pw.close()
                } catch(e: Exception) {

                }
            }

            return response!!
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            this.callback.onResult(result)
        }

    }
}