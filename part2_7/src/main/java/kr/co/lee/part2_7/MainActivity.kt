package kr.co.lee.part2_7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kr.co.lee.part2_7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        val listener = object : Animation.AnimationListener() {
//            override fun onAnimationStart(animation: Animation?) {
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                val anim = AnimationUtils.loadAnimation(this@MainActivity, R.anim.move)
//                // 마지막 상황에 멈추게, 안 그러면 복귀
//                anim.fillAfter = true
//                binding.img.startAnimation(anim)
//            }
//
//            override fun onAnimationRepeat(animation: Animation?) {
//            }
//
//        }

        // 애니메이션 획득
        val anim = AnimationUtils.loadAnimation(this, R.anim.`in`)
        // anim.setAnimationListener(listener)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                val anim = AnimationUtils.loadAnimation(this@MainActivity, R.anim.move)
                // 마지막 상황에 멈추게, 안 그러면 복귀
                anim.fillAfter = true
                binding.img.startAnimation(anim)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        // 애니메이션 시작
        binding.img.startAnimation(anim)
    }
}