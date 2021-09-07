package kr.co.lee.part5_15

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Lab15_2Activity : AppCompatActivity(){

    lateinit var keyBoardBtn: Button
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab15_2)

        keyBoardBtn = findViewById(R.id.lab2_toggleBtn)
        editText = findViewById(R.id.lab2_edit)

        keyBoardBtn.setOnClickListener {
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onPause() {
        super.onPause()
        showToast("onPause...")
    }

    override fun onResume() {
        super.onResume()
        showToast("onResume...")
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            if(isInMultiWindowMode) {
                showToast("onResume....isInMultiWindowMode...yes")
            }
        }
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        super.onMultiWindowModeChanged(isInMultiWindowMode)
        showToast("onMultiWindowModeChanged...$isInMultiWindowMode")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("portrait...")
        } else {
            showToast("landscape...")
        }
    }
}