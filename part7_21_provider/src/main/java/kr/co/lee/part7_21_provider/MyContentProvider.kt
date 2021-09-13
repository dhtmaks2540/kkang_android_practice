package kr.co.lee.part7_21_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MyContentProvider : ContentProvider() {
    lateinit var db: SQLiteDatabase

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val id = db.delete("tb_data", selection, selectionArgs)
        return id
    }

    override fun getType(uri: Uri): String? {
        // at the given URI
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        db.insert("tb_data", null, values)
        return null
    }

    // Content Provider는 최초 한번만 실행(생명주기가 이것뿐)
    // 부팅이 완료되면 휴대폰에 설치된 모든 앱의 컨텐트 프로바이더는 실행이 된다
    override fun onCreate(): Boolean {
        val helper = DBHelper(context!!)
        db = helper.writableDatabase
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor = db.query("tb_data", projection, selection, selectionArgs, null, null, sortOrder)
        return cursor!!
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val id = db.update("tb_data", values, selection, selectionArgs)
        return id
    }
}