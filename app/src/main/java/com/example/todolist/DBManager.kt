package com.example.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log

class DBManager
{
    val DB_NAME="NoteDatabase"
    val DB_TABLE="NoteTable"
    val DB_VERSION=1
    val colID="ID"
    val colTitle="Title"
    val colDesc="Desc"
    val sqlCommand="CREATE TABLE IF NOT EXISTS "+ DB_TABLE +" ("+ colID +" INTEGER PRIMARY KEY, "+ colTitle +" TEXT, "+ colDesc +" TEXT);"
    val TAG="aaa"
    var sqldb:SQLiteDatabase ?=null
    constructor(context: Context)
    {
        var db=DatabaseHelper(context)
        sqldb=db.writableDatabase

    }
    inner class DatabaseHelper(context: Context):SQLiteOpenHelper
        (context,DB_NAME,null,DB_VERSION)
    {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCommand)
            Log.d(TAG, "onCreate DatabaseHelper: ")
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE +"")
            Log.d(TAG, "onUpgrade")
        }

    }

    fun insert(values:ContentValues):Long
    {
        var idInsrt=sqldb!!.insert(DB_TABLE,null,values)
        return idInsrt
    }

    fun RunQuery(columns:Array<String>,selection:String,selectionArgs:Array<String>,
        sortOrder:String):Cursor
    {
        val qb= SQLiteQueryBuilder()
        qb.tables=DB_TABLE
        val cursor = qb.query(sqldb,columns,selection,selectionArgs,null,
        null, sortOrder)
        return cursor
    }

    fun deleteNote(selection: String,selectionArgs:Array<String>):Int
    {
        var delRow = sqldb!!.delete(DB_TABLE,selection,selectionArgs)
        return delRow
    }

    fun updateNote(contentValues: ContentValues,selection: String,
    selectionArgs:Array<String>):Int
    {
        var idUpdate= sqldb!!.update(DB_TABLE,contentValues,selection,
        selectionArgs)
        return idUpdate
    }
}