package kr.co.lee.part7_19

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var datas: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lab1_list)
        datas = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, datas)
        listView.adapter = adapter

        // 배터리 상황을 얻기 위한 IntentFilter 생성
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        // Receiver 객체를 null로 주고 IntenteFilter를 넣으면 시스템의 배터리 상태의 정보값만 얻을 수 있다
        val batteryStatus = registerReceiver(null, ifilter)

        // 배터리 상태
        val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        // 충전 상태 여부
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
        
        // 충전중이라면
        if(isCharging) {
            // 배터리 충전 플러그 정보
            val chargePlug = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
            val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
            if(usbCharge) {
                addListItem("Battery is USB Charging")
            } else if(acCharge) {
                addListItem("Battery is AC Charging")
            }
        } else {
            addListItem("Battery State is not Charging")
        }

        // 배터리 확인
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = (level?.toFloat()!! / scale?.toFloat()!!) * 100
        addListItem("Current Battery : $batteryPct%")

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.PROCESS_OUTGOING_CALLS), 100)
        }

        // BroadCast Receiver 등록
        registerReceiver(brOn, IntentFilter(Intent.ACTION_SCREEN_ON))
        registerReceiver(brOff, IntentFilter(Intent.ACTION_SCREEN_OFF))
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_POWER_CONNECTED))
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_POWER_DISCONNECTED))
    }

    // BroadcastReceiver 클래스를 상속받으며 onReceiver 메소드 오버라이드 -> BroadcastReceiver 객체 선언
    val brOn = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            addListItem("screen on")
        }
    }

    val brOff = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            addListItem("screen off...")
        }
    }

    val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
                addListItem("ON Connected...")
            } else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                addListItem("On Disconnected...")
            }
        }
    }

    private fun addListItem(message: String) {
        datas.add(message)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        // BroadcastReceiver 해제
        unregisterReceiver(brOn)
        unregisterReceiver(brOff)
        unregisterReceiver(batteryReceiver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100 && grantResults.isNotEmpty()) {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                val toast = Toast.makeText(this, "no permission", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }
}