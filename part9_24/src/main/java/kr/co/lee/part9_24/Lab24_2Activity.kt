package kr.co.lee.part9_24

import android.os.Bundle
import android.sax.RootElement
import android.util.Xml
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import javax.xml.parsers.DocumentBuilderFactory

class Lab24_2Activity : AppCompatActivity() {

    lateinit var domBtn: Button
    lateinit var saxBtn: Button
    lateinit var pullBtn: Button
    lateinit var jsonBtn: Button

    lateinit var cityView: TextView
    lateinit var tempView: TextView
    lateinit var resultView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab24_2)

        domBtn = findViewById(R.id.lab2_dom)
        saxBtn = findViewById(R.id.lab2_sax)
        pullBtn = findViewById(R.id.lab2_pull)
        jsonBtn = findViewById(R.id.lab2_json)

        cityView = findViewById(R.id.lab2_city)
        tempView = findViewById(R.id.lab2_temperature)
        resultView = findViewById(R.id.lab2_result_title)

        val buttonListener = View.OnClickListener {
            when(it) {
                domBtn -> {
                    domParsing()
                }
                saxBtn -> {
                    saxParsing()
                }
                pullBtn -> {
                    pullParsing()
                }
                jsonBtn -> {
                    jsonParsing()
                }
            }
        }

        domBtn.setOnClickListener(buttonListener)
        saxBtn.setOnClickListener(buttonListener)
        pullBtn.setOnClickListener(buttonListener)
        jsonBtn.setOnClickListener(buttonListener)
    }

    // 객체 형식으로 xml을 파싱하여 처리
    private fun domParsing() {
        try {
            // 파일 읽기
            val inputStream = assets.open("test.xml")
            // Dom에 파싱
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc = builder.parse(inputStream, null)

            // 태그 attribute 값 획득
            val tempElement =
                (doc.getElementsByTagName("temperature").item(0)) as org.w3c.dom.Element
            val temperature = tempElement.getAttribute("value")

            val cityElement = (doc.getElementsByTagName("city").item(0) as org.w3c.dom.Element)
            val city = cityElement.getAttribute("name")

            // 결과 출력
            resultView.text = "DOM Parsing Result"
            cityView.text = city
            tempView.text = temperature
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 이벤트를 등록하여 xml을 처리
    private fun saxParsing() {
        resultView.text = "SAX Parsing Result"

        val root = RootElement("current")
        val cityElement = root.getChild("root")
        val tempElement = root.getChild("temperature")

        cityElement.setStartElementListener {
            cityView.text = it.getValue("name")
        }

        tempElement.setStartElementListener {
            tempView.text = it.getValue("value")
        }

        try {
            val inputStream = assets.open("test.xml")
            Xml.parse(inputStream, Xml.Encoding.UTF_8, root.contentHandler)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 이벤트를 등록하여 xml을 처리, 중간에 끊을 수 있음
    private fun pullParsing() {
        resultView.text = "Pull Parsing Result"

        try {
            val inputStream = assets.open("test.xml")
            val parser = Xml.newPullParser()
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            var done = false

            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                var name: String? = null
                if(eventType == XmlPullParser.START_TAG) {
                    name = parser.name
                    if(name.equals("city")) {
                        cityView.text = parser.getAttributeValue(null, "name")
                    } else if(name.equals("temperature")) {
                        tempView.text = parser.getAttributeValue(null, "value")
                    }
                }
                eventType = parser.next()
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    // Json 파싱
    private fun jsonParsing() {
        resultView.text = "JSON Parsing Result.."
        var json: String? = null
        try {
            val input = assets.open("test.json")
            val size = input.available()
            val buffer = byteArrayOf()
            input.read(buffer)
            input.close()
            val json = String(buffer, charset("UTF-8"))
            val root = JSONObject(json)

            cityView.text = root.getString("name")
            val main = root.getJSONObject("main")
            tempView.text = main.getString("temp")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}