package kr.co.lee.part8_22

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var onOffView: ImageView
    lateinit var allProviderView: TextView
    lateinit var enableProviderView: TextView
    lateinit var providerView: TextView
    lateinit var latitudeView: TextView
    lateinit var longitudeView: TextView
    lateinit var accuracyView: TextView
    lateinit var timeView: TextView

    lateinit var manager: LocationManager

    var enabledProviders: List<String>? = null
    var bestAccuracy: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onOffView = findViewById(R.id.lab1_onOffView)
        allProviderView = findViewById(R.id.lab1_allProviders)
        enableProviderView = findViewById(R.id.lab1_enableProviders)
        providerView = findViewById(R.id.lab1_provider)
        latitudeView = findViewById(R.id.lab1_latitude)
        longitudeView = findViewById(R.id.lab1_longitude)
        accuracyView = findViewById(R.id.lab1_accuracy)
        timeView = findViewById(R.id.lab1_time)

        // LocationManager 얻어오기
        manager = getSystemService(LOCATION_SERVICE) as LocationManager

        // 권한 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }
    }

    // requestPermission 후 자동으로 호출되는 메소드(
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 퍼미션 여부 확인
        if(requestCode == 100 && grantResults.isNotEmpty()) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getProviders()
                getLocation()
            } else {
                showToast("no permission")
            }
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun getProviders() {
        var result = "All Providers : "
        // LocationManager에서 모든 provider 불러오기
        val providers = manager.allProviders
        for(provider in providers) {
            result += "$provider,"
        }
        allProviderView.text = result

        result = "Enabled Providers : "
        // LocationManager에서 사용 가능 provider 불러오기
        enabledProviders = manager.getProviders(true)
        for(provider in enabledProviders!!) {
            result += "$provider,"
        }
        enableProviderView.text = result
    }

    private fun getLocation() {
        for(provider in enabledProviders!!) {
            var location: Location? = null
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // LocationManager에 provider를 제공해서 위치 얻어오기
                location = manager.getLastKnownLocation(provider)
            } else {
                showToast("no permission")
            }
            if(location != null) {
                // 정확도
                val accuracy = location.accuracy
                // 정확도를 비교해서 가장 정확한 정보 얻어오기
                if(bestAccuracy == 0.0f) {
                    bestAccuracy = accuracy
                    setLocationInfo(provider, location)
                } else {
                    if(accuracy < bestAccuracy) {
                        bestAccuracy = accuracy
                        setLocationInfo(provider, location)
                    }
                }
            }
        }
    }

    private fun setLocationInfo(provider: String, location: Location) {
        if(location != null) {
            // 위치 정보 제공자
            providerView.text = provider
            // 위도
            latitudeView.text = location.latitude.toString()
            // 경도
            longitudeView.text = location.longitude.toString()
            // 정확도
            accuracyView.text = "${location.accuracy}m"
            // 획득 시간
            val date = Date(location.time)
            val sd = SimpleDateFormat("yyyy-MM-dd HH:mm")
            timeView.text = sd.format(date)
            onOffView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_on, null))
        } else {
            showToast("location null")
        }
    }
}