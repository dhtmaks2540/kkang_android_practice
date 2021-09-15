package kr.co.lee.part8_mission

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var map: GoogleMap? = null

    lateinit var markerOptions: MarkerOptions
    lateinit var locationView: TextView
    lateinit var apiClient: GoogleApiClient
    lateinit var providerClient: FusedLocationProviderClient

    var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 권한 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }

        locationView = findViewById(R.id.mission1_text)
        markerOptions = MarkerOptions()

        // GoogleApiClient가 connect() 함수로 인해 location provider가 사용 가능해지면 호출되는 리스너
        val callListener = object : GoogleApiClient.ConnectionCallbacks {
            // 연결 성공
            override fun onConnected(p0: Bundle?) {
                if (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // FusedLocationProviderClient의 getLastLocation으로 위치값 획득
                    // 이 위치는 리스너로 등록한 콜백 함수로 전달
                    providerClient.lastLocation.addOnSuccessListener {
                        initMap(it.latitude, it.longitude)
                    }
                    // 연결 해제
                    apiClient.disconnect()
                }
            }

            // 연결 중단
            override fun onConnectionSuspended(p0: Int) {

            }

        }

        // GoogleApiClient 연결 실패 리스너
        val failListener = GoogleApiClient.OnConnectionFailedListener {  }

        // GoogleApiClient 객체 초기화
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(callListener)
            .addOnConnectionFailedListener(failListener)
            .build()

        // ProviderClient 객체 초기화
        providerClient = LocationServices.getFusedLocationProviderClient(this)

        (supportFragmentManager.findFragmentById(R.id.mission1_map) as SupportMapFragment).getMapAsync(
            OnMapReadyCallback {
                map = it

                // map을 움직이고 끝났을 때 호출되는 리스너
                map?.setOnCameraIdleListener {
                    // 마커 삭제
                    marker?.remove()

                    // map의 카메라 포지션에서 위도, 경도 얻어오기(중앙값)
                    val latitude = map?.cameraPosition?.target?.latitude
                    val longitude = map?.cameraPosition?.target?.longitude

                    // marker options의 위치 얻어온 위도 경도 넣기
                    markerOptions.position(LatLng(latitude!!, longitude!!))

                    // 마커 추가
                    marker = map?.addMarker(markerOptions)

                    val reverseGeoCodingThread = ReverseGeoCodingThread(latitude!!, longitude!!)
                    reverseGeoCodingThread.start()
                }
            })
    }

    // 위도, 경도를 얻어와서 주소로 변경
    inner class ReverseGeoCodingThread(val latitude: Double, val longitude: Double): Thread() {
        override fun run() {
            // Geocoder 클래스를 이용해 Geocoding, Reverse Geocoding 사용
            val geocoder = Geocoder(this@MainActivity)
            var addresses: List<Address>? = null
            var addressText = ""
            try {
                // getFromLocation 메소드에 인자로 위도, 경도를 넣어 주소 얻어오기
                addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if(addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    addressText = "${address.adminArea} ${if(address.maxAddressLineIndex > 0) address.locality else ""} "

                    val txt = address.subLocality
                    if(txt != null) {
                        addressText += "$txt "
                        addressText += "${address.thoroughfare} ${address.subThoroughfare}"
                    }

                    // 주소의 정보를 핸들러에게 보내기위해 메시지 객체 생성후 전달
                    val msg = Message()
                    msg.what = 200
                    msg.obj = addressText
                    handler.sendMessage(msg)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 주소를 받으면 UI를 변경하는 핸들러
    val handler = object: Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                200 -> {
                    locationView.text = msg.obj as String
                }
            }
        }
    }

    // map 초기화(자신의 위치를 얻어와서 map을 그 위치로 움직이고 마커 추가)
    private fun initMap(latitude: Double, longitude: Double) {
        if(map != null) {
            val latLng = LatLng(latitude, longitude)
            val position = CameraPosition.Builder()
                .target(latLng).zoom(16f).build()
            map?.moveCamera(CameraUpdateFactory.newCameraPosition(position))

            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start))
            markerOptions.position(latLng)

            marker = map?.addMarker(markerOptions)

            val reverseGeoCodingThread = ReverseGeoCodingThread(latitude, longitude)
            reverseGeoCodingThread.start()
        }
    }

    override fun onResume() {
        super.onResume()

        // GoogleApiClient 연결
        apiClient.connect()
    }
}