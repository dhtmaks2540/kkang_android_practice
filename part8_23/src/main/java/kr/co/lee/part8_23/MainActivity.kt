package kr.co.lee.part8_23

import android.graphics.BitmapFactory
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(){

    var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.lab1_map) as SupportMapFragment).getMapAsync(
            OnMapReadyCallback {
                map = it

                if(map != null) {
                    val latLng = LatLng(37.566610, 126.978403)
                    val position = CameraPosition.Builder()
                        .target(latLng).zoom(16f).build()
                    map?.moveCamera(CameraUpdateFactory.newCameraPosition(position))

                    val markerOptions = MarkerOptions()
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    markerOptions.position(latLng)
                    markerOptions.title("서울시청")
                    markerOptions.snippet("Tel:01-120")

                    map?.addMarker(markerOptions)

                    val address = "서울툭별시 중구 서소문동 37-9"
                    val reverseThread = MyReverseGeocodingThread(address)
                    reverseThread.start()
                }
            })
    }

    inner class MyReverseGeocodingThread(val address: String): Thread() {
        override fun run() {
            val geocoder = Geocoder(this@MainActivity)
            try {
                // address를 가지고 geocoder를 사용해 위도 경도를 얻기
                val results = geocoder.getFromLocationName(address, 1)
                val resultAddress = results[0]
                val latLng = LatLng(resultAddress.latitude, resultAddress.longitude)

                val msg = Message()
                msg.what = 200
                msg.obj = latLng
                handler.sendMessage(msg)
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                200 -> {
                    val markerOptions = MarkerOptions()
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    markerOptions.position(msg.obj as LatLng)
                    markerOptions.title("서울시립미술관")
                    map?.addMarker(markerOptions)
                }
            }
        }
    }
}