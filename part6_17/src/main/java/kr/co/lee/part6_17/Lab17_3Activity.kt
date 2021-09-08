package kr.co.lee.part6_17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class Lab17_3Activity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab17_3)

        // 인스턴스화
        viewPager = findViewById<ViewPager2>(R.id.lab2_pager)

        val pagerAdapter = MyPagerAdapter(this)
        viewPager.adapter = pagerAdapter

    }

    override fun onBackPressed() {
        // 만약 첫번째 뷰페이지라면 activity에 finish를 부른다
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            // 그 빡의 경우 전단계로 돌아가기
            viewPager.currentItem = viewPager.currentItem - 1
        }

    }

    class MyPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        companion object {
            // 페이지 개수 정적 변수로 선언
            private const val NUM_PAGES = 2
        }

        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {
            if (position == 0) {
                return OneFragment()
            } else if (position == 1) {
                return ThreeFragment()
            } else {
                return TwoFragment()
            }
        }

    }
}