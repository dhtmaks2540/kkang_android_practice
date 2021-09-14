package kr.co.lee.part8_22

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class Lab22_2Activity : AppCompatActivity() {
    lateinit var onOffView: ImageView
    lateinit var latitudeView: TextView
    lateinit var longitudeView: TextView
    lateinit var accuracyView: TextView
    lateinit var timeView: TextView

    lateinit var apiClient: GoogleApiClient
    lateinit var providerClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab22_2)

        onOffView = findViewById(R.id.lab2_onOffView)
        latitudeView = findViewById(R.id.lab2_latitude)
        longitudeView = findViewById(R.id.lab2_longitude)
        accuracyView = findViewById(R.id.lab2_accuracy)
        timeView = findViewById(R.id.lab2_time)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }

        // connect() 함수로 인해 결정된 위치 정보 제공자가 사용 가능해지면 호출되는 리스너
        val callListener = object : GoogleApiClient.ConnectionCallbacks {
            // 연결 성공 시
            override fun onConnected(p0: Bundle?) {
                if (ContextCompat.checkSelfPermission(
                        this@Lab22_2Activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // FusedLocationProviderClient의 getLastLocation으로 위치값 획득
                    // 이 위치는 리스너로 등록한 콜백 함수로 전달
                    providerClient.lastLocation.addOnSuccessListener {
                        setLocationInfo(it)
                    }
                    // 연결 해제
                    apiClient.disconnect()
                }
            }

            // 연결 중단 시
            override fun onConnectionSuspended(p0: Int) {
                showToast("onConnectionSuspended")
            }

        }

        // 연결 실패 리스너
        val failListener = GoogleApiClient.OnConnectionFailedListener { showToast("onConnectionFailed") }

        // GOogleApiClient 객체 초기화
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(callListener)
            .addOnConnectionFailedListener(failListener)
            .build()

        // providerClient 객체 초기화
        providerClient = LocationServices.getFusedLocationProviderClient(this)

    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun setLocationInfo(location: Location) {
        if (location != null) {
            latitudeView.text = location.latitude.toString()
            longitudeView.text = location.longitude.toString()
            accuracyView.text = "${location.accuracy}m"
            val date = Date(location.time)
            val sd = SimpleDateFormat("yyyy-MM-dd HH:mm")
            timeView.text = sd.format(date)
            onOffView.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_on,
                    null
                )
            )
        } else {
            showToast("location null")
        }
    }

    override fun onResume() {
        super.onResume()

        // GoogleApiClient 연결
        apiClient.connect()
    }
}