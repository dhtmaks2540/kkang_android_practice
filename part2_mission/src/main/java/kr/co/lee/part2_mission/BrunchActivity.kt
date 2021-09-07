package kr.co.lee.part2_mission

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog

class BrunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brunch)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("작성 중인 내용을 저장하지 않고 나가시겠습니다?")
                builder.setNegativeButton("취소", null)
                builder.setPositiveButton("확인"
                ) { dialog, which -> finish() }

                val alertDialog = builder.create()
                alertDialog.show()
            }
        }

        return super.onKeyDown(keyCode, event)
    }
}