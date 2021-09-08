package kr.co.lee.part6_17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button

    lateinit var manager: FragmentManager
    lateinit var oneFragment: OneFragment
    lateinit var twoFragment: TwoFragment
    lateinit var threeFragment: ThreeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById(R.id.main_btn1)
        btn2 = findViewById(R.id.main_btn2)
        btn3 = findViewById(R.id.main_btn3)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)

        manager = supportFragmentManager
        oneFragment = OneFragment()
        twoFragment = TwoFragment()
        threeFragment = ThreeFragment()

        val tf = manager.beginTransaction()
        tf.addToBackStack(null)
        tf.add(R.id.main_container, oneFragment)
        tf.commit()
    }

    override fun onClick(v: View?) {
        when(v) {
            btn1 -> {
                if(!oneFragment.isVisible) {
                    val ft = manager.beginTransaction()
                    ft.addToBackStack(null)
                    ft.replace(R.id.main_container, oneFragment)
                    ft.commit()
                }
            }
            btn2 -> {
                if(!twoFragment.isVisible) {
                    twoFragment.show(manager, null)
                }
            }
            btn3 -> {
                if(!threeFragment.isVisible) {
                    val ft = manager.beginTransaction()
                    ft.addToBackStack(null)
                    ft.replace(R.id.main_container, threeFragment)
                    ft.commit()
                }
            }
        }
    }
}