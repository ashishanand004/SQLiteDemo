package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_Name = "MyStudentDB";
    public static String Table_Name = "student";
    public static String Column_Name = "name";
    public static String Column_Contact = "contact";
    public static String Column_Address = "address";


    public DBHelper(@Nullable Context context) {
        super(context, DB_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create TABLE " +Table_Name +" (id integer primary key AUTOINCREMENT," +
            Column_Name +" text,"+
            Column_Contact +" text," +
            Column_Address +" text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Table_Name);
        onCreate(db);

    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from "+Table_Name+" where id="+id ,null);
            Log.d("test","cursor row count : "+cursor.getColumnCount());
        cursor.moveToFirst();
        Log.d("test","cursor row id : " +cursor.getString(cursor.getColumnIndex("id")));
        return cursor;
    }

    public long insertStudentData(String name, String contact, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_Name, name);
        contentValues.put(Column_Contact, contact);
        contentValues.put(Column_Address, address);
        return db.insert(Table_Name, null, contentValues);

    }

    public int updateStudentData(int id, String name, String contact, String address ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_Name, name);
        contentValues.put(Column_Contact, contact);
        contentValues.put(Column_Address, address);
        return db.update(Table_Name, contentValues, "id=?",new String[]{Integer.toString(id)});
    }

    public int deleteStudentData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_Name, "id=?",new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllStudent() {
        ArrayList<String> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Table_Name, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            studentList.add(cursor.getString(cursor.getColumnIndex(Column_Name)));
            cursor.moveToNext();
        }
        cursor.close();
        return  studentList;
    }
}
