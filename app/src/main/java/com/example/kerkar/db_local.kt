package com.example.kerkar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList

private var arrayListId: ArrayList<String> = arrayListOf()
private var arrayListName: ArrayList<String> = arrayListOf()
private var arrayListType: ArrayList<Int> = arrayListOf()
private var arrayListBitmap: ArrayList<Boolean> = arrayListOf()

private val TAG ="localdb"

class db_setting(
        val tableName: String = "SampleTable",
        val dbName: String = "testDB",
        val dbVersion: Int = 1,

        val tbname_tmp: String = "tmp_db",
        val tbname_time: String = "timetable_db",
        val tbname_userdb: String = "user_db"
)

class tmp_local_DB(val context: Context?){
    val dbSetting = db_setting()
    val dbVersion = dbSetting.dbVersion
    val tbtmp = dbSetting.tbname_tmp
    val dbName = dbSetting.dbName
    val userdb = dbSetting.tbname_userdb
    private val TAG = "tmp_db"




    fun insert_tmp(tmp:String){
        try{
            val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
            val db = dbHelper.writableDatabase

            val values = ContentValues()
            values.put("value", tmp)

            db.replaceOrThrow(tbtmp, null, values)
            Log.d(TAG, "insert tmpdb")


        }catch(exception: Exception){
            Log.e(TAG, exception.toString())
        }
    }


    fun get_tmp(): Array<String> {
//        val arraylist: ArrayList<String> = arrayListOf()
        var list: Array<String> = arrayOf()
        Log.d(TAG, "call get tmp")
        try{
            val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
            val database = dbHelper.readableDatabase


            val sql = "select value from ${tbtmp}"
            Log.d(TAG, "sql: ${sql}")

            val cursor = database.rawQuery(sql, null)
            Log.d(TAG, "cursor: ${cursor}")


            if(cursor.count >0){
                Log.d(TAG, "in")
//                Log.d(TAG, list.toString())
                cursor.moveToFirst()
                while(!cursor.isAfterLast){
                    Log.d(TAG, "in-2")
//                    arraylist.add(cursor.getString(0))
                    list += cursor.getString(0)
                    cursor.moveToNext()
                }

            }
            Log.d(TAG, "out")
//            Log.d(TAG, list.toString())






        }catch(exception: Exception){
            Log.e(TAG, "error: "+exception.toString())
        }

        return list
    }


    fun clear(){
        Log.d(TAG, "call clear")
        try{
            val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
            val database = dbHelper.readableDatabase

            val sql = "delete from ${tbtmp}"

            database.delete(tbtmp, "value", null)


        }catch(exception: Exception){
            Log.e(TAG, exception.toString())
        }
    }
}

class action_local_DB(val context: Context?){
    val dbSetting = db_setting()
    val dbVersion = dbSetting.dbVersion
    val tbtmp = dbSetting.tbname_tmp
    val dbName = dbSetting.dbName
    val userdb = dbSetting.tbname_userdb


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
        var sql = "create table if not exists ${setting.tbname_tmp} (value text primary key)"
        db?.execSQL(sql)
        Log.d("db_local", "create tb :tmp")


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
            var sql = "alter table ${setting.tbname_tmp} add column deleteFlag integer default 0"
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