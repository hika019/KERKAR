package com.example.kerkar

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class local_db(context: Context, databaseName: String, factory: SQLiteDatabase.CursorFactory?, version: Int): SQLiteOpenHelper(context, databaseName, factory, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table if not exists SampleTable (id text primary key, name text, type integer, image BLOB)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("alter table SampleTable add column deleteFlag integer default 0")
    }

}