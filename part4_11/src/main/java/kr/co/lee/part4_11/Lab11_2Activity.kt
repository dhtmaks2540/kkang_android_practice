package kr.co.lee.part4_11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import kr.co.lee.part4_11.databinding.ActivityLab112Binding

class Lab11_2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab112Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLab112Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings = binding.webview.settings
        settings.javaScriptEnabled = true

        binding.webview.loadUrl("file:///android_asset/test.html")

        binding.webview.addJavascriptInterface(JavascriptTest(), "android")
        binding.webview.webViewClient = MyWebClient()
        binding.webview.webChromeClient = MyWebChrome()

        val listener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                when(v) {
                    binding.btnChartLine -> {
                        binding.webview.loadUrl("javascript:lineChart()")
                    }
                    binding.btnChartBar -> {
                        binding.webview.loadUrl("javascript:barChart()")
                    }
                }
            }

        }

        binding.btnChartBar.setOnClickListener(listener)
        binding.btnChartLine.setOnClickListener(listener)
    }

    class JavascriptTest {
//        @JavascriptInterface
//        fun getChartData(): String {
//            val buffer = StringBuffer()
//            buffer.append("[")
//            for(value in 0 until 14) {
//                buffer.append("[$value, ${Math.sin(value as Double)}]")
//
//                Log.d("kkang", "$value,${Math.sin(value as Double)}")
//                if(value < 13) buffer.append(",")
//            }
//            buffer.append("]")
//            return buffer.toString()
//        }

        @JavascriptInterface
        fun getChartData(): String = buildString {
            append("[")
            for (value in 0 until 14) {
                append("[$value, ${Math.sin(value as Double)}]")

                Log.d("kkang", "$value,${Math.sin(value as Double)}")
                if (value < 13) append(",")
            }
            append("]")
        }
    }

    inner class MyWebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val t = Toast.makeText(this@Lab11_2Activity, request.toString(), Toast.LENGTH_SHORT)
            t.show()
            return true
        }
    }

    inner class MyWebChrome : WebChromeClient() {
        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            val t = Toast.makeText(this@Lab11_2Activity, message, Toast.LENGTH_SHORT)
            t.show()
            result?.confirm()
            return true
        }
    }
}