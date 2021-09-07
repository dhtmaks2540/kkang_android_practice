package kr.co.lee.part3_mission

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DATABASE_VERSION: Int = 1

class DBHelper: SQLiteOpenHelper {
    constructor(context: Context): super(context, "contactdb", null, DATABASE_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        val sql: String = """
            |create table tb_contact (
            |_id integer primary key autoincrement,
            |name not null,
            |phone,
            |email)
        """.trimMargin("|")

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(newVersion == DATABASE_VERSION) {
            db?.execSQL("drop table tb_contact")
            onCreate(db)
        }
    }
}