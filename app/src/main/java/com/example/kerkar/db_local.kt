package com.example.kerkar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.security.MessageDigest
import java.util.ArrayList

private var arrayListId: ArrayList<String> = arrayListOf()
private var arrayListName: ArrayList<String> = arrayListOf()
private var arrayListType: ArrayList<Int> = arrayListOf()
private var arrayListBitmap: ArrayList<Boolean> = arrayListOf()

class db_setting(
        val tableName: String = "SampleTable",
        val dbName: String = "testDB",
        val dbVersion: Int = 1,

        val tbname_assignment: String = "assignment_db",
        val tbname_time: String = "timetable_db",
        val tbname_userdb: String = "user_db"
)

class action_local_DB(val context: Context?){
    val dbSetting = db_setting()
    val dbVersion = dbSetting.dbVersion
    val tbName = dbSetting.tableName
    val dbName = dbSetting.dbName
    val userdb = dbSetting.tbname_userdb




    fun insert_assignmnet_data(
            tableName: String,
            day:String, time:String, subject:String, assignment_name: String, note: String?, type: Int
    ){
        try{
            val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
            val db = dbHelper.writableDatabase

            val str = time + subject + assignment_name

            val id = id_generator(str)

            val values = ContentValues()
            values.put("id", id)
            values.put("day", day)
            values.put("time", time)
            values.put("subject_id", subject)
            values.put("assignment_name", assignment_name)
            values.put("note", note)
            values.put("type", type)


            db.insertOrThrow(tableName, null, values)

        }catch(exception: Exception){
            Log.d("local_db_action", exception.toString())
        }
    }

    fun insert_timetable(week: String, period: String, lecture_name: String, teacher_name: String, class_name: String){
        try{
            val setting = db_setting()

            val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
            val db = dbHelper.writableDatabase

            val str = week + period + lecture_name + teacher_name + class_name
            val id = id_generator(str)

            val values = ContentValues()
            values.put("week_to_day_and_period", week+period)
            values.put("id", id)
            values.put("week_to_day", week)
            values.put("period", period)
            values.put("lecture_name", lecture_name)
            values.put("teacher_name", teacher_name)
            values.put("class_name", class_name)

            db.replaceOrThrow(setting.tbname_time, null, values)
            Log.d("local_db_action", "replaced timetable db")

        }catch (exception: Exception){
            Log.d("local_db_action", exception.toString())
        }
    }

    fun insert_userdb(uid: String, college: String, mail: String, password: String){
        try {
            val setting = db_setting()

            val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
            val db = dbHelper.writableDatabase

            val values = ContentValues()
            values.put("uid", uid)
            values.put("college", college)
            values.put("email", mail)
            values.put("password", password)

            db.replaceOrThrow(setting.tbname_userdb, null, values)
            Log.d("local_db_action", "replaced timetable db")

        }catch (exception: Exception){
            Log.d("local_db_action", exception.toString())
        }
    }





}


class local_DBHelper(context: Context, databaseName: String, factory: SQLiteDatabase.CursorFactory?, version: Int): SQLiteOpenHelper(context, databaseName, factory, version){

    private val setting = db_setting()

    val version = setting.dbVersion

    //テーブル作成
    override fun onCreate(db: SQLiteDatabase?) {
        var sql = "create table if not exists ${setting.tbname_assignment} (id text primary key, day text, time text, subject_id text, assignment_name text, note text, type integer)"
        db?.execSQL(sql)
        Log.d("db_local", "create tb :課題")


        sql = "create table if not exists ${setting.tbname_time} " +
                "(week_to_day_and_period text primary key, id text , week_to_day text, period text, lecture_name text, teacher_name text, class_name text)"
        db?.execSQL(sql)
        Log.d("db_local", "create tb :時間割")


        sql = "create table if not exists ${setting.tbname_userdb} " +
                "(uid text primary key, college text , email text, password text)"
        db?.execSQL(sql)
        Log.d("db_local", "create tb :user_db")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            var sql = "alter table ${setting.tbname_assignment} add column deleteFlag integer default 0"
            db?.execSQL(sql)
            Log.d("db", "update tb :課題")

            sql = "alter table ${setting.tbname_userdb} add column deleteFlag integer default 0"
            db?.execSQL(sql)
            Log.d("db", "update tb :user_db")

            sql = "alter table ${setting.tbname_time} add column deleteFlag integer default 0"
            db?.execSQL(sql)
            Log.d("db", "update tb :時間割")

        }
    }

}