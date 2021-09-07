package kr.co.lee.part4_12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import kr.co.lee.part4_12.databinding.ActivityLab122Binding

class Lab12_2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLab122Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab122Binding.inflate(layoutInflater)
        setContentView(binding.root)

        registerForContextMenu(binding.imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_lab2, menu)

        try {
            val method =
                menu?.javaClass?.getDeclaredMethod("setOptionalIconsVisible", Boolean.javaClass)
            method?.isAccessible = true
            method?.invoke(menu, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val menuItem = menu?.findItem(R.id.menu_main_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.setQuery("", false)
                searchView.isIconified = true
                showToast(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0, 0, 0, "서버전송")
        menu?.add(0, 1, 0, "보관함에 보관")
        menu?.add(0, 2, 0, "삭제")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            0 -> {
                showToast("서버 전송이 선택")
            }
            1 -> {
                showToast("보관함에 보관이 선택")
            }
            2 -> {
                showToast("삭제가 선택")
            }
        }

        return true
    }

    private fun showToast(message: String) {
        val t = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        t.show()
    }
}