package kr.co.lee.part4_mission1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_VERSION = 2

class DBHelper: SQLiteOpenHelper {
    constructor(context: Context): super(context, "calldb", null, DATABASE_VERSION)

    // 앱이 설치되고 실행될 때 한번만 호출
    override fun onCreate(db: SQLiteDatabase?) {
        val tableSql = "create table tb_calllog (" +
                "_id integer primary key autoincrement," +
                "name not null," +
                "photo," +
                "date," +
                "phone)"

        // create table sql문 실행
        db?.execSQL(tableSql)

        // 테이블에 데이터 insert
        db!!.execSQL("insert into tb_calllog (name, photo, date, phone) values ('홍길동','yes','휴대전화, 1일전','010001')")
        db.execSQL("insert into tb_calllog (name, photo, date, phone) values ('류현진','no','휴대전화, 1일전','010001')")
        db.execSQL("insert into tb_calllog (name, photo, date, phone) values ('강정호','no','휴대전화, 2일전','010001')")
        db.execSQL("insert into tb_calllog (name, photo, date, phone) values ('김현수','yes','휴대전화, 2일전','010001')")
        db.execSQL("insert into tb_calllog (name, photo, date, phone) values ('오승환','no','휴대전화, 2일전','010001')")
        db.execSQL("insert into tb_calllog (name, photo, date, phone) values ('이대호','no','휴대전화, 3일전','010001')")
        db.execSQL("insert into tb_calllog (name, photo, date, phone) values ('박병호','no','휴대전화, 3일전','010001')")
        db.execSQL("insert into tb_calllog (name, photo, date, phone) values ('추신수','no','휴대전화, 3일전','010001')")
    }

    // 버전이 변경되거나 하면 자동으로 호출되는 메소드
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(newVersion == DATABASE_VERSION) {
            db?.execSQL("drop table tb_calling")
            onCreate(db)
        }
    }
}