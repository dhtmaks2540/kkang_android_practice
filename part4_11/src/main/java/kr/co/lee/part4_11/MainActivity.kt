package kr.co.lee.part4_11

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.content.res.ResourcesCompat
import kr.co.lee.part4_11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spanView.movementMethod = ScrollingMovementMethod()

        val data = "복수초 \n img \n 이른봄 설산에서 만나는 복수초는" +
                "모든 야생화 찍사들의 로망이 아닐까 싶다."
        val builder = SpannableStringBuilder(data)
        var start = data.indexOf("img")
        if (start > -1) {
            val end = start + "img".length
            val dr = ResourcesCompat.getDrawable(resources, R.drawable.img1, null)
            dr?.setBounds(0, 0, dr.intrinsicWidth, dr.intrinsicHeight)
            val span = ImageSpan(dr!!)
            println("start : $start, end : $end")
            builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        start = data.indexOf("복수초")
        if (start > -1) {
            val end = start + "복수초".length
            val styleSpan = StyleSpan(Typeface.BOLD)
            val sizeSpan = RelativeSizeSpan(2.0f)
            println("start2 : $start, end2 : $end")
            builder.setSpan(styleSpan, start, end + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(sizeSpan, start, end + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.spanView.text = builder

        val html = "<font color='RED'>얼레지</font> <br/>" +
                "<img src='img1'/> <br/> 곰배령에서 만난 봄꽃"
        binding.htmlView.text = Html.fromHtml(html, MyImageGetter(), null)
    }

    inner class MyImageGetter : Html.ImageGetter {
        override fun getDrawable(source: String?): Drawable? {
            if (source.equals("img1")) {
                val dr = ResourcesCompat.getDrawable(resources, R.drawable.img2, null)
                dr?.setBounds(0, 0, dr.intrinsicHeight, dr.intrinsicWidth)
                return dr!!
            }
            return null
        }

    }
}