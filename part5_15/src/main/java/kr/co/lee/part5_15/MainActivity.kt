package kr.co.lee.part5_15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var listView: ListView
    lateinit var datas: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>

    lateinit var detailBtn: Button
    lateinit var dialogBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.main_list)
        detailBtn = findViewById(R.id.main_btn_detail)
        dialogBtn = findViewById(R.id.main_btn_dialog)

        detailBtn.setOnClickListener(this)
        dialogBtn.setOnClickListener(this)

        datas = ArrayList()
        datas.add("onCreate...")

        adapter = ArrayAdapter(this, R.layout.item_main_list, datas)
        listView.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v) {
            detailBtn -> {
                val intent = Intent(this, DetailActivity::class.java)
                startActivity(intent)
            }
            dialogBtn -> {
                val intent = Intent(this, DialogActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        datas.add("onResume...")
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()

        datas.add("onPause...")
        adapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()

        datas.add("onStart...")
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()

        datas.add("onStop...")
        adapter.notifyDataSetChanged()
    }

    override fun onRestart() {
        super.onRestart()

        datas.add("onRestart...")
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()

        datas.add("onDestroy...")
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        datas.add("onSaveInstanceState....")
        adapter.notifyDataSetChanged()

        outState.putString("data1", "hello")
        outState.putInt("data2", 100)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        datas.add("onRestoreInstanceState....")
        adapter.notifyDataSetChanged()

        val data1 = savedInstanceState.getString("data1")
        val data2 = savedInstanceState.getInt("data2")

        val toast = Toast.makeText(this, "$data1 : $data2", Toast.LENGTH_SHORT)
        toast.show()
    }
}