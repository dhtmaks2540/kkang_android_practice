package kr.co.lee.part3_8

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_VERSION = 1

class DBHelper: SQLiteOpenHelper {

    constructor(context: Context) : super(context, "memodb", null, DATABASE_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        val memoSQL = """
           |create table tb_memo
           |(_id integer primary key autoincrement,
           |title,
           |content)
        """.trimMargin("|")

        db?.execSQL(memoSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(newVersion == DATABASE_VERSION) {
            db?.execSQL("drop table tb_memo")
            onCreate(db)
        }
    }
}