package kr.co.lee.part2_5

import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import kr.co.lee.part2_5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnVibration.setOnClickListener {
            val vib: Vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vib.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vib.vibrate(1000)
            }

        }

        binding.btnSystemBeep.setOnClickListener {
            val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val ringtone: Ringtone = RingtoneManager.getRingtone(applicationContext, notification)

            ringtone.play()
        }

        binding.btnCustomSound.setOnClickListener {
            val player: MediaPlayer = MediaPlayer.create(this, R.raw.fallbackring)
            player.start()
        }
    }
}