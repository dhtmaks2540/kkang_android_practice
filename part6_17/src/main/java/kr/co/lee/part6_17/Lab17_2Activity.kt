package kr.co.lee.part6_17

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

class Lab17_2Activity: AppCompatActivity() {
    val TARGET_SETTING_PAGE = "target"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingFragment = SettingPreferenceFragment()
        val intent = intent
        if(intent != null) {
            val rootKey = intent.getStringExtra(TARGET_SETTING_PAGE)
            if(rootKey != null) {
                val args = Bundle()
                args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, rootKey)
                settingFragment.arguments = args
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, settingFragment, null)
            .commit()
    }
}