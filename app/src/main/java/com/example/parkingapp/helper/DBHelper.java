package com.example.parkingapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "parking_app.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(id integer primary key, username text, password text)");
        sqLiteDatabase.execSQL("create table motorcycles(id integer primary key, name text)");
        sqLiteDatabase.execSQL("create table cars(id integer primary key, name text)");
        sqLiteDatabase.execSQL("create table car_vips(id integer primary key, name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists motorcycles");
        sqLiteDatabase.execSQL("drop table if exists cars");
        sqLiteDatabase.execSQL("drop table if exists car_vips");
    }

    public Cursor getUser() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users", null);
        return cursor;
    }

    public void createUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        db.insert("users", null, cv);
    }

    public Cursor get(int index) {
        SQLiteDatabase db = getWritableDatabase();
        String table = index == 0 ? "motorcycles" : index == 1 ? "cars" : "car_vips";
        Cursor cursor = db.rawQuery("Select * from " + table, null);
        cursor.moveToFirst();
        Log.i("home", "placed");
        return cursor;
    }

    public Boolean change(String command, int index, String prev, String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);

        String table = index == 0 ? "motorcycles" : index == 1 ? "cars" : "cars_vip";
        Cursor cursor = db.rawQuery("Select * from " + table + " where name = ?", new String[]{prev});
        long result;

        if (command == "update") {
            if (cursor.getCount() > 0) {
                result = db.update(table, cv, "name=?", new String[]{prev});
            } else {
                return false;
            }
        } else if (command == "delete") {
            result = db.delete(table, "name=?", new String[]{prev});
        } else {
            result = db.insert(table, null, cv);
        }

        return result > 0;
    }
}
