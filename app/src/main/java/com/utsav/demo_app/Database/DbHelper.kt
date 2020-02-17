package com.utsav.demo_app.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_FNAME
                + " TEXT," + COLUMN_LNAME
                + " TEXT," + COLUMN_DOB
                + " TEXT," + COLUMN_MN
                + " TEXT," + COLUMN_IMAGE_PATH
                + " TEXT" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addUser(userDetail: UserDetail) {
        val values = ContentValues()
        values.put(COLUMN_FNAME, userDetail.firstName)
        values.put(COLUMN_LNAME, userDetail.lastName)
        values.put(COLUMN_DOB, userDetail.dateOfBirth)
        values.put(COLUMN_MN, userDetail.mobileNumber)
        values.put(COLUMN_IMAGE_PATH, userDetail.image_path)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun updateById(id: Int,
                   firstname: String,
                   lastname: String,
                   dob: String,
                   mobilenumber: String,
                   imagepath: String): Int {
        val cv = ContentValues()
        cv.put(COLUMN_FNAME, firstname)
        cv.put(COLUMN_LNAME, lastname)
        cv.put(COLUMN_DOB,dob)
        cv.put(COLUMN_MN, mobilenumber)
        cv.put(COLUMN_IMAGE_PATH, imagepath)
        val whereclause = "$COLUMN_ID=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, cv, whereclause, whereargs)
    }

    fun getAllUser(): List<UserDetail> {
        val userlist: ArrayList<UserDetail> = ArrayList<UserDetail>()
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var firstName: String
        var lastName: String
        var dob: String
        var mobilenubmer: String
        var image_path:String

        if (cursor.moveToFirst()) {
            do {
               firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FNAME))
                lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LNAME))
                dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB))
                mobilenubmer= cursor.getString(cursor.getColumnIndex(COLUMN_MN))
                image_path= cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH))

                val emp = UserDetail(firstName = firstName, lastName= lastName, dateOfBirth= dob,mobileNumber =mobilenubmer,image_path = image_path )
                userlist.add(emp)
            } while (cursor.moveToNext())
        }
        return userlist
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "mindorksName.db"
        val TABLE_NAME = "name"
        val COLUMN_ID = "_id"
        val COLUMN_FNAME = "firstname"
        val COLUMN_LNAME = "lastname"
        val COLUMN_DOB = "dateofbirth"
        val COLUMN_MN = "mobilenumber"
        val COLUMN_IMAGE_PATH = "image_path"

    }

}