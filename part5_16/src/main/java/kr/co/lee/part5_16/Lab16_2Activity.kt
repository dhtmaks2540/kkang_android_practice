package kr.co.lee.part5_16

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class Lab16_2Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var startView: ImageView
    lateinit var pauseView: ImageView
    lateinit var textView: TextView

    var isFirst = true

    lateinit var asyncTask: MyAsyncTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab16_2)

        startView = findViewById(R.id.main_startBtn)
        pauseView = findViewById(R.id.main_pauseBtn)
        textView = findViewById(R.id.main_textView)

        startView.setOnClickListener(this)
        pauseView.setOnClickListener(this)

        asyncTask = MyAsyncTask()
    }

    override fun onClick(v: View?) {
        when(v) {
            startView -> {
                if(isFirst) {
                    asyncTask.isRun = true
                    asyncTask.execute()
                    isFirst = false
                } else {
                    asyncTask.isRun = true
                }
            }
            pauseView -> {
                asyncTask.isRun = false
            }
        }
    }

    inner class MyAsyncTask: AsyncTask<Unit, Int, String>() {
        var loopFlag = true
        var isRun = false

        override fun doInBackground(vararg params: Unit?): String {
            var count = 10
            while(loopFlag) {
                SystemClock.sleep(1000)
                if(isRun) {
                    count--
                    publishProgress(count)
                    if(count == 0) {
                        loopFlag = false
                    }
                }
            }
            return "Finish!!"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            textView.text = values[0].toString()
        }

        override fun onPostExecute(result: String?) {
            textView.text = result
        }
    }
}