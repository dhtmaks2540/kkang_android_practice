package kr.co.lee.part9_25

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class HttpRequester {
    var http: HttpTask? = null

    fun request(url: String, param: HashMap<String, String>, callback: HttpCallback) {
        http = HttpTask(url, param, callback)
        http?.execute()
    }

    class HttpTask(val url: String, val param: HashMap<String, String>, val callback: HttpCallback):
            AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String {
            var response = ""
            var postData = ""
            var pw: PrintWriter? = null
            var `in`: BufferedReader? = null

            try {
                val text = URL(url)
                val http = text.openConnection() as HttpURLConnection
                http.setRequestProperty(
                    "Content-type",
                    "application/x-www-form-urlencoded;charset=UTF-8"
                )
                http.connectTimeout = 10000
                http.readTimeout = 10000
                http.requestMethod = "POST"
                http.doInput = true
                http.doOutput = true
                // 서버에 전송하기 데이터를 웹의 Query 문자열 형식으로 변형
                if (param != null && param.size > 0) {
                    val entries: Iterator<Map.Entry<String, String>> = param.entries.iterator()
                    var index = 0
                    while (entries.hasNext()) {
                        if (index != 0) {
                            postData = "$postData&"
                        }
                        val mapEntry = entries.next()
                        postData = postData + mapEntry.key + "=" + URLEncoder.encode(
                            mapEntry.value,
                            "UTF-8"
                        )
                        index++
                    }
                    // 데이터 전송
                    pw = PrintWriter(OutputStreamWriter(http.outputStream, "UTF-8"))
                    pw.write(postData)
                    pw.flush()
                }
                ///서버로부터 데이터 수신
                `in` = BufferedReader(InputStreamReader(http.inputStream, "UTF-8"))
                val sb = StringBuffer()
                var inputLine: String?
                while (`in`.readLine().also { inputLine = it } != null) {
                    sb.append(inputLine)
                }
                response = sb.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    pw?.close()
                } catch (e: Exception) {
                }
                try {
                    `in`?.close()
                } catch (e: Exception) {
                }
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            callback.onResult(result)
        }

    }
}