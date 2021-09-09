package kr.co.lee.part6_18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawer: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    var isDrawerOpend: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawer = findViewById(R.id.main_drawer)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.main_drawer_view)
        navigationView.setNavigationItemSelectedListener {
            val id = it.itemId
            when(id) {
                R.id.menu_drawer_home -> {
                    showToast("NavigationDrawer...home...")
                }
                R.id.menu_drawer_message -> {
                    showToast("NavigationDrawer...message...")
                }
                R.id.menu_drawer_add -> {
                    showToast("NavigationDrawer...add...")
                }
            }
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return false
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}