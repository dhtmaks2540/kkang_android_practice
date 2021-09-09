package kr.co.lee.part6_mission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var tabLayout: TabLayout
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coordinatorLayout = findViewById(R.id.main_coordinator)
        tabLayout = findViewById(R.id.main_tablayout)
        collapsingToolbarLayout = findViewById(R.id.main_collapsing)
        toolbar = findViewById(R.id.main_toolbar)

        collapsingToolbarLayout.title = " "

        setSupportActionBar(toolbar)
        
        val viewPager = findViewById<ViewPager2>(R.id.main_viewpager)
        // ViewPager adapter 연결
        viewPager.adapter = MyPagerAdapter(this)

        // TabLayout과 ViewPager2 연결
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "TAB$position"
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    class MyPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

        companion object {
            private const val NUM_PAGES = 3
        }

        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> OneFragment()
                1 -> TwoFragment()
                else -> ThreeFragment()
            }
        }

    }
}