package kr.co.lee.part2_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TabHost
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = findViewById<TabHost>(R.id.host)
        host.setup()

        var spec: TabHost.TabSpec = host.newTabSpec("tab1")
        spec.setIndicator(null, ResourcesCompat.getDrawable(resources, R.drawable.tab_icon1, null))
        spec.setContent(R.id.tab_content1)
        host.addTab(spec)

        spec = host.newTabSpec("tab2")
        spec.setIndicator(null, ResourcesCompat.getDrawable(resources, R.drawable.tab_icon2, null))
        spec.setContent(R.id.tab_content2)
        host.addTab(spec)

        spec = host.newTabSpec("tab3")
        spec.setIndicator(null, ResourcesCompat.getDrawable(resources, R.drawable.tab_icon3, null))
        spec.setContent(R.id.tab_content3)
        host.addTab(spec)
    }
}