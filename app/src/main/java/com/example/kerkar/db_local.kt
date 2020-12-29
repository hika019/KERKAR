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

        val tbname_sub: String = "sub_dn",
        val tbname_unsub: String = "unsub_db",
        val tbname_time: String = "timetable_db")

class action_local_DB(val context: Context?){
    val dbSetting = db_setting()
    val dbVersion = dbSetting.dbVersion
    val tbName = dbSetting.tableName
    val dbName = dbSetting.dbName

    fun id_generator(str: String): String{
        val id = MessageDigest.getInstance("SHA-256")
                .digest(str.toByteArray())
                .joinToString(separator = "") {
                    "%02x".format(it)
                }
        return id
    }


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





}


class local_DBHelper(context: Context, databaseName: String, factory: SQLiteDatabase.CursorFactory?, version: Int): SQLiteOpenHelper(context, databaseName, factory, version){

    private val setting = db_setting()

    val version = setting.dbVersion

    //テーブル作成
    override fun onCreate(db: SQLiteDatabase?) {
        var sql = "create table if not exists ${setting.tbname_sub} (id text primary key, day text, time text, subject_id text, assignment_name text, note text, type integer)"
        db?.execSQL(sql)
        Log.d("db_local", "create tb :提出済み課題")

        sql = "create table if not exists ${setting.tbname_unsub} (id text primary key, day text, time text, subject_id text, assignment_name text, note text, type integer)"
        db?.execSQL(sql)
        Log.d("db_local", "create tb :未提出課題")

        sql = "create table if not exists ${setting.tbname_time} (id text primary key, time text, subject text, class text, teacher text)"
        db?.execSQL(sql)
        Log.d("db_local", "create tb :時間割")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            var sql = "alter table ${setting.tbname_sub} add column deleteFlag integer default 0"
            db?.execSQL(sql)
            Log.d("db", "update tb :提出済み課題")

            sql = "alter table ${setting.tbname_unsub} add column deleteFlag integer default 0"
            db?.execSQL(sql)
            Log.d("db", "update tb :未提出課題")

            sql = "alter table ${setting.tbname_time} add column deleteFlag integer default 0"
            db?.execSQL(sql)
            Log.d("db", "update tb :時間割")

        }
    }

}