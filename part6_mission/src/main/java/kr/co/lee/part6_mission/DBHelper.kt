package kr.co.lee.part6_mission

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_VERSION = 5

class DBHelper(context: Context): SQLiteOpenHelper(context, "datadb", null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val tableSql = "create table tb_data (" +
                "_id integer primary key autoincrement," +
                "name not null," +
                "date)"

        db!!.execSQL(tableSql)

        db.execSQL("insert into tb_data (name,date) values ('안영주','2017-07-01')")
        db.execSQL("insert into tb_data (name,date) values ('최은경','2017-07-01')")
        db.execSQL("insert into tb_data (name,date) values ('최호성','2017-07-01')")
        db.execSQL("insert into tb_data (name,date) values ('정성택','2017-06-30')")
        db.execSQL("insert into tb_data (name,date) values ('정길용','2017-06-30')")
        db.execSQL("insert into tb_data (name,date) values ('임윤정','2017-06-29')")
        db.execSQL("insert into tb_data (name,date) values ('김종덕','2017-06-29')")
        db.execSQL("insert into tb_data (name,date) values ('채규태','2017-06-28')")
        db.execSQL("insert into tb_data (name,date) values ('원형섭','2017-06-28')")
        db.execSQL("insert into tb_data (name,date) values ('표선영','2017-06-28')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion == DATABASE_VERSION) {
            db!!.execSQL("drop table tb_data")
            onCreate(db)
        }
    }

}