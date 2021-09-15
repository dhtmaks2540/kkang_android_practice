package kr.co.lee.part9_24

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.ServiceState
import android.telephony.TelephonyManager
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var datas: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lab1_listview)

        datas = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, datas)
        listView.adapter = adapter

        // 전화기로서의 다양한 정보와 각종 상태 변경 상황을 감지하는 클래스
        val telManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        // listen 함수의 매개변수로 리스너와 어느 상태 변화를 감지할 것인지
        telManager.listen(
            listener,
            PhoneStateListener.LISTEN_CALL_STATE or PhoneStateListener.LISTEN_SERVICE_STATE
        )

        // TelePhonyManager에 있는 네트워크 국가 정보와 네트워크 망 사업자 정보
        datas.add("countryIos:" + telManager.networkCountryIso)
        datas.add("operatorName:" + telManager.networkOperatorName)

        // 네트워크 타입 얻어오기
        if(telManager.networkType == TelephonyManager.NETWORK_TYPE_LTE) {
            datas.add("networkType : LTE")
        } else if(telManager.networkType == TelephonyManager.NETWORK_TYPE_HSDPA) {
            datas.add("networkType : 3G")
        }

        // 권한이 허용되어 있다면 사용자 전화번호 얻어오기
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            datas.add("PhoneNumber : ${telManager.line1Number}")
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 100)
        }

        checkNetwork()

        checkWifi()

        // 브로드 캐스트 리시버를 이용해 와이파이 상태 감지
        val wifiFilter = IntentFilter()
        wifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        wifiFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        registerReceiver(wifiReceiver, wifiFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(wifiReceiver)
    }

    // 스마트폰의 상태 변경 감지하는 클래스
    val listener = object : PhoneStateListener() {
        // 단말기 서비스 상태 변경
        override fun onServiceStateChanged(serviceState: ServiceState?) {
            // serviceState의 getState로 상태값 얻음
            when (serviceState?.state) {
                // 긴급 통화만
                ServiceState.STATE_EMERGENCY_ONLY -> {
                    datas.add("onServiceStateChanged STATE_EMERGENCY_ONLY")
                }
                // 서비스 가능
                ServiceState.STATE_IN_SERVICE -> {
                    datas.add("onServiceStateChanged STATE_IN_SERVICE")
                }
                // 서비스 불가
                ServiceState.STATE_OUT_OF_SERVICE -> {
                    datas.add("onServiceStateChanged STATE_OUT_OF_SERVICE")
                }
                // 비행모드 등 전화 기능 꺼놓음
                ServiceState.STATE_POWER_OFF -> {
                    datas.add("onServiceStateChanged STATE_POWER_OFF")
                }
            }
            // 데이터 변경 적용
            adapter.notifyDataSetChanged()
        }

        // 전화가 걸려온 상태를 감지
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            when (state) {
                // 통화 대기 상태
                TelephonyManager.CALL_STATE_IDLE ->
                    datas.add("onCallStateChanged : CALL_STATE_IDLE : $phoneNumber")
                // 통화벨 울림
                TelephonyManager.CALL_STATE_RINGING ->
                    datas.add("onCallStateChanged : CALL_STATE_RINGING : $phoneNumber")
                // 통화 중인 상태
                TelephonyManager.CALL_STATE_OFFHOOK ->
                    datas.add("onCallStateChanged : CALL_STATE_OFFHOOK: $phoneNumber")
            }
            adapter.notifyDataSetChanged()
        }


    }

    private fun checkNetwork() {
        // 네트워크 접속 정보를 확인하기 위한 ConnectivityManager 클래스
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
//        val networkInfo2 = connectivityManager.getNetworkCapabilities()
        // 연결이 되어 있을 때
        if(networkInfo != null) {
            if(networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                datas.add("Network Info : Online - ${networkInfo.typeName}")
            } else if(networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                datas.add("Network Info : Online - ${networkInfo.typeName}")
            }
            // 연결이 안되어 있을 때
        } else {
            datas.add("network info : offline")
        }

        adapter.notifyDataSetChanged()
    }

    private fun checkWifi() {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        if(!wifiManager.isWifiEnabled) {
            datas.add("WifiManager : wifi disabled")
            if(wifiManager.wifiState != WifiManager.WIFI_STATE_ENABLED) {
                wifiManager.isWifiEnabled = true
            }
        } else {
            datas.add("WifiManager : wifi enabled")
        }
        adapter.notifyDataSetChanged()
    }

    val wifiReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // wifi 상태 변경
            if(intent?.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
                val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1)
                // 와이파이가 가능상태
                if(state == WifiManager.WIFI_STATE_ENABLED) {
                    datas.add("WIFI_STATE_CHANGED_ACTION : enable")
                // 와이파이가 불가능 상태
                } else {
                    datas.add("WIFI_STATE_CHANGED_ACTION : disable")
                }
            // 와이파이 네트워크 연결 상태 변경
            } else if(intent?.action == WifiManager.NETWORK_STATE_CHANGED_ACTION) {
                val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
                val wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                val ssid = wifiInfo.ssid
                if(networkInfo?.state == NetworkInfo.State.CONNECTED) {
                    datas.add("NETWORK_STATE_CHANGED_ACTION : connected...$ssid")
                } else if(networkInfo?.state == NetworkInfo.State.DISCONNECTED) {
                    datas.add("NETWORK_STATE_CHANGED_ACTION : disconnected...$ssid")
                }
            // 현재 연결된 와이파이 네트워크 신호 세기 변경
            } else if(intent?.action == WifiManager.RSSI_CHANGED_ACTION) {
                datas.add("RSSI_CHANGED_ACTION")
            }
            adapter.notifyDataSetChanged()
        }

    }
}