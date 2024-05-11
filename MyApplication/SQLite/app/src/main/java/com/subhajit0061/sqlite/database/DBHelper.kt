package com.subhajit0061.sqlite.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    companion object {

        const val DATABASE_NAME = "contacts.db"
        const val VERSION = 1
        const val TABLE_NAME = "contacts"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_NUMBER = "number"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create =
            "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_NUMBER TEXT)"

        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val upgrade = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(upgrade)
        onCreate(db)
    }
}