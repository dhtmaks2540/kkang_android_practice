package kr.co.lee.part2_5

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kr.co.lee.part2_5.databinding.ActivityLab52Binding
import kr.co.lee.part2_5.databinding.ActivityMainBinding
import java.util.*

class Lab5_2Activity : AppCompatActivity(){

    // 이벤트 처리를 위해 dialog 객체를 멤버변수로 선언
    var alertDialog: AlertDialog? = null
    var customDialog: AlertDialog? = null
    var listDialog: AlertDialog? = null

    lateinit var binding: ActivityLab52Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLab52Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAlert.setOnClickListener {  }

        binding.btnAlert.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setTitle("알림")
            builder.setMessage("정말 종료?")
            builder.setPositiveButton("OK", dialogListener)
            builder.setNegativeButton("NO", null)

            alertDialog = builder.create()
            alertDialog?.show()
        }

        binding.btnList.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("알람 벨소리")
            builder.setSingleChoiceItems(R.array.dialog_array, 0, dialogListener)
            builder.setPositiveButton("확인", null)
            builder.setNegativeButton("취소", null)
            listDialog = builder.create()
            listDialog?.show()
        }

        binding.btnDate.setOnClickListener {
            // 현재 날짜로 dialog를 띄우기 위해 날짜를 구한다
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dateDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    showToast("$year:${(month + 1)}:$dayOfMonth")
                }, year, month, day)

            dateDialog.show()
        }

        binding.btnTime.setOnClickListener {
            // 현재 시간으로 Dialog를 띄우기 위해 시간을 구함
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                showToast("$hourOfDay:$minute")
            }, hour, minute, false)

            timePickerDialog.show()
        }

        binding.btnCustom.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            // custom dialog를 위한 layout xml 초기화
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = inflater.inflate(R.layout.dialog_layout, null)
            builder.setView(view)

            builder.setPositiveButton("확인", dialogListener)
            builder.setNegativeButton("취소", null)

            customDialog = builder.create()
            customDialog?.show()
        }
    }

    // 매개변수의 문자열을 Toast로 띄우는 개발자 함수
    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    // Dialog Button 이벤트 처리
    val dialogListener = DialogInterface.OnClickListener { dialog, which ->
        when {
            dialog == customDialog && which == DialogInterface.BUTTON_POSITIVE -> {
                showToast("custom dialog 확인 clkci...")
            }
            dialog == listDialog -> {
                // 목록 dialog의 항목이 선택되었을 때 항목 문자열 획득
                val datas = resources.getStringArray(R.array.dialog_array)
                showToast("${datas[which]} 선택하였다")
            }
            dialog == alertDialog && which == DialogInterface.BUTTON_POSITIVE -> {
                showToast("alert dialog ok click..")
            }
        }
    }
}