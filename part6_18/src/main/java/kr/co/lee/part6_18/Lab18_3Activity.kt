package kr.co.lee.part6_18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class Lab18_3Activity : AppCompatActivity() {

    lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab18_3)

        val toolBar = findViewById<Toolbar>(R.id.lab3_toolbar)
        setSupportActionBar(toolBar)

        val recyclerView = findViewById<RecyclerView>(R.id.lab3_recycler)

        val list = ArrayList<String>()
        for (i in 0 until 20) {
            list.add("Item=$i")
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(list)

        val fab = findViewById<FloatingActionButton>(R.id.lab3_fab)
        coordinatorLayout = findViewById(R.id.lab3_coordinator)

        fab.setOnClickListener {
            Snackbar.make(coordinatorLayout, "I am SnackBar", Snackbar.LENGTH_LONG)
                .setAction("MoreAction") {

                }.show()
        }

    }

    private class MyAdapter : RecyclerView.Adapter<MyViewHoldr> {
        private val list: List<String>

        constructor(list: List<String>) {
            this.list = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoldr {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return MyViewHoldr(view)
        }

        override fun onBindViewHolder(holder: MyViewHoldr, position: Int) {
            val text = list.get(position)
            holder.title.text = text
        }

        override fun getItemCount(): Int = list.size

    }

    private class MyViewHoldr(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(android.R.id.text1)
    }
}