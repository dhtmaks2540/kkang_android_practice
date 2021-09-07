package kr.co.lee.part2_mission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kr.co.lee.part2_mission.databinding.ActivityFacebookBinding

class FacebookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFacebookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFacebookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.faceBtn.setOnClickListener {
            val toast = Toast.makeText(this, "ok button click~~~", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}