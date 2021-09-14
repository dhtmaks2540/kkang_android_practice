package kr.co.lee.part7_21

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Lab21_2Activity : AppCompatActivity() {

    lateinit var contactBtn: Button
    lateinit var nameView: TextView
    lateinit var phoneView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab21_2)

        contactBtn = findViewById(R.id.lab2_btn)
        nameView = findViewById(R.id.lab2_name)
        phoneView = findViewById(R.id.lab2_phone)

        // 버튼 리스너
        val listener = View.OnClickListener {
            when(it) {
                contactBtn -> {
                    // 주소록앱 인텐트를 발생
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.data = Uri.parse("content://com.android.contacts/data/phones")
                    startActivityForResult(intent, 10)
                }
            }
        }

        contactBtn.setOnClickListener(listener)

        // 권한을 확인하여 허용되지 않았다면 다이얼로그로 권한 요청
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10 && resultCode == RESULT_OK) {
            // 전화번호부에서 넘어온 식별자를 얻어와서 id로 지정
            val id = Uri.parse(data?.dataString).lastPathSegment
            val cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                arrayOf(ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER),
                ContactsContract.Data._ID+"="+id, null, null)

            cursor?.moveToFirst()
            val name = cursor?.getString(0)
            val phone = cursor?.getString(1)
            nameView.text = name
            phoneView.text = phone
        }
    }
}