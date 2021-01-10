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

class timetable_local_DB(val context: Context?){
    val dbSetting = db_setting()
    val dbVersion = dbSetting.dbVersion
    val dbName = dbSetting.dbName
    val tbtimetable = dbSetting.tbname_time


    fun insert_timetable(id: String, week_to_day: String, course: String, lecturer: ArrayList<String>, room: String){
        try {


            val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
            val db = dbHelper.writableDatabase

            val lecturer_str = lecturer.toString()
            Log.d(TAG, "call timetable insert")

            val values = ContentValues()
            values.put("id", id)
            values.put("week_to_day", week_to_day)
            values.put("course", course)
            values.put("lecturer", lecturer_str)
            values.put("room", room)

            db.replaceOrThrow(tbtimetable, null, values)
            Log.d(TAG, "replaced timetable db")

        }catch (exception: Exception){
            Log.e(TAG, exception.toString())
        }
    }

    fun get_timetable(): Array<Any> {
        var list: Array<Any> = arrayOf()
        val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
        val database = dbHelper.readableDatabase
        val sql = "select * from ${tbtimetable}"

        Log.d(TAG, "call get timetable")
        try {
            val cursor = database.rawQuery(sql, null)
            Log.d(TAG, "call get timetable ")

            if(cursor.count > 0){
                cursor.moveToFirst()
                while(!cursor.isAfterLast){

                    val in_data = hashMapOf<String, Any>(
                            "id" to cursor.getString(0),
                            "week_to_day" to cursor.getString(1),
                            "course" to cursor.getString(2),
                            "lecturer" to cursor.getString(3),
                            "room" to cursor.getString(4)
                    )

                    list += in_data
                    cursor.moveToNext()
                }
            }


        }catch (exception: Exception){
            Log.e(TAG, "call get timetable -> excption")
            Log.e(TAG, exception.toString())
        }
        return list
    }



    fun clear(){
        Log.d(TAG, "call clear in timetable")
        try{
            val dbHelper = local_DBHelper(context!!, dbName, null, dbVersion)
            val database = dbHelper.readableDatabase

            val sql = "delete * from ${tbtimetable}"

            database.delete(tbtimetable, "value", null)


        }catch(exception: Exception){
            Log.e(TAG, exception.toString())
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
                "(id text primary key, week_to_day text, course text , lecturer text, room text)"
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