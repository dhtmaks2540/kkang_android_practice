package kr.co.lee.part2_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import kr.co.lee.part2_6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    lateinit var binding: ActivityMainBinding
    var initx: Float = 0.0f
    var initTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bellName.setOnClickListener(this)
        binding.label.setOnClickListener(this)

        binding.repeatCheck.setOnCheckedChangeListener(this)
        binding.vibrate.setOnCheckedChangeListener(this)
        binding.onOff.setOnCheckedChangeListener(this)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.bellName -> {
                showToast("bell text click event")
            }
            binding.label -> {
                showToast("label text click event")
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView) {
            binding.repeatCheck -> {
                showToast("repeat checkbox is $isChecked")
            }
            binding.vibrate -> {
                showToast("vibrate checkbox is $isChecked")
            }
            binding.onOff -> {
                showToast("switch is $isChecked")
            }
            else -> {
                showToast("wrong button")
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                initx = event.getRawX()
            }
            MotionEvent.ACTION_UP -> {
                val diffX = initx - event?.getRawX()
                if(diffX > 30) {
                    showToast("???????????? ?????? ?????????")
                } else if(diffX <= 30) {
                    showToast("??????????????? ?????? ?????????")
                }
            }
        }

        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if(System.currentTimeMillis() - initTime > 3000) {
                    // back button??? ????????? 3?????? ???????????????
                    showToast("??????????????? ?????? ??? ?????????")
                    // ?????? ?????? ??????
                    initTime = System.currentTimeMillis()
                } else {
                    // 3??? ????????? BackButton??? ??? ??? ?????? ??????, Activity ??????
                    finish()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}