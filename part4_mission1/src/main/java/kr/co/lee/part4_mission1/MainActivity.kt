package kr.co.lee.part4_mission1

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kr.co.lee.part4_mission1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var callPhonePermission: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Permission 체크
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callPhonePermission = true
        }

        // permission 부여 안 될 경우 permission 요청(dialog)
        if(!callPhonePermission) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 200)
        }

        // SqlOpenHelper를 상속하여 구현한 클래스 불러오기
        val helper = DBHelper(this)
        val db = helper.writableDatabase
        // Select로 모든 데이터 불러오기
        val cursor = db.rawQuery("select * from tb_calllog", null)

        val datas = ArrayList<CallLogVO>()
        // 값이 존재한다면
        while(cursor.moveToNext()) {
            val vo = CallLogVO(cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4))
            datas.add(vo)
        }
        db.close()

        val adapter = CallLogAdapter(this, R.layout.list_item, datas)
        binding.listview.adapter = adapter
    }

    // permission 부여 요청 결과 확인
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 200 && grantResults.size > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhonePermission = true
            }
        }
    }
}