package kr.co.lee.part6_17

import android.R.attr.key
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.preference.*


// 설정 XML 파일을 정용하기 위해 PreferenceFragmentCompat을 상속받는 클래스 만든다
class SettingPreferenceFragment : PreferenceFragmentCompat() {
    val TARGET_SETTING_PAGE = "target"

    lateinit var prefs: SharedPreferences

    lateinit var soundPreference: Preference
    lateinit var keywordSoundPreference: Preference
    lateinit var keywordScreen: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // preference XML 파일 적용
        setPreferencesFromResource(R.xml.settings_preference, rootKey)

        if (rootKey == null) {
            // Preference 객체 얻어오기
            soundPreference = findPreference<Preference>("sound_list")!!
            keywordSoundPreference = findPreference<Preference>("keyword_sound_list")!!
            keywordScreen = findPreference<Preference>("keyword_screen")!!

            // Preference는 SharedPreferences를 사용해 데이터를 저장하는데 그 객체를 가져온다
            prefs = PreferenceManager.getDefaultSharedPreferences(activity)
            // SharedPreferences에서 값을 가져오는데 그것의 값이 있다면
            if (!prefs.getString("sound_list", "").equals("")) {
                // List의 summary를 key가 sound_list인 값으로 설정
                soundPreference.summary = prefs.getString("sound_list", "카톡")
            }

            if (!prefs.getString("keyword_sound_list", "").equals("")) {
                keywordSoundPreference.summary = prefs.getString("keyword_sound_list", "카톡")
            }

            if (prefs.getBoolean("keyword", false)) {
                keywordScreen.summary = "사용"
            }

            // 사용자가 설정을 변경할 때마다 리스너 객체 자동 실행
            prefs.registerOnSharedPreferenceChangeListener(prefListener)
        }
    }

    // 설정 변경 이벤트 처리
    val prefListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            // key값으로 어떤 설정이 변경되었는지를 식별하여 summary를 변경
            if (key.equals("sound_list")) {
                soundPreference.summary = prefs.getString("sound_list", "카톡")
            }
            if (key.equals("keyword_sound_list")) {
                keywordSoundPreference.summary = prefs.getString("keyword_sound_list", "카톡")
            }
            if (key.equals("keyword")) {
                if (prefs.getBoolean("keyword", false)) {
                    keywordScreen.summary = "사용"
                } else {
                    keywordScreen.summary = "사용안함"
                }
            }
        }

    // 내부 PreferenceScreen에 의한 화면 이동 처리
    override fun onNavigateToScreen(preferenceScreen: PreferenceScreen?) {
        val intent = Intent(activity, Lab17_2Activity::class.java)
        intent.putExtra(TARGET_SETTING_PAGE, preferenceScreen?.key)
        Toast.makeText(activity, preferenceScreen?.key, Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}