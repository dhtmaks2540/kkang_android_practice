package kr.co.lee.part6_18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Lab18_2Activity : FragmentActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var fab: FloatingActionButton
    lateinit var constraintLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab18_2)

        constraintLayout = findViewById(R.id.lab2_container)
        viewPager = findViewById(R.id.lab2_viewpager)
        viewPager.adapter = MyPagerAdapter(this)

        val tabLayout = findViewById<TabLayout>(R.id.lab2_tabs)
        // TabLayout과 ViewPager2 연동
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Tab - $position"
        }.attach()

        // FloatingButton 초기화
        fab = findViewById(R.id.lab2_fab)
        fab.setOnClickListener {
            // 스낵바 생성(스낵바가 보여질 뷰, 메시지, 시간)
            // setAction을 통해 이벤트 처리
            Snackbar.make(constraintLayout, "I am SnackBar", Snackbar.LENGTH_LONG).
                    setAction("MoreAction", View.OnClickListener {
                        Toast.makeText(this, "SnackBar안에서의 이벤트!", Toast.LENGTH_SHORT).show()
                    }).show()
        }
    }

    // ViewPager2에 적용할 Adapter
    class MyPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

        companion object {
            private const val NUM_FRAGEMENT = 3
        }

        override fun getItemCount(): Int = NUM_FRAGEMENT

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    OneFragment()
                }
                1 -> {
                    TwoFragment()
                }
                else -> {
                    ThreeFragment()
                }
            }
        }

    }
}