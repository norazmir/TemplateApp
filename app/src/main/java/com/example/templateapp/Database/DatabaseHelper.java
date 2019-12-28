package com.example.templateapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ServiceManagement.db";

    public DatabaseHelper(@Nullable Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table contractor(id integer primary key autoincrement, contractor_name text not null, contractor_email text not null unique, password text, confirm_password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists contractor");
    }

    public boolean loginCreateContractor(String contractor_name, String contractor_email, String password, String confirm_password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contractor_name", contractor_name);
        contentValues.put("contractor_email", contractor_email);
        contentValues.put("password", password);
        contentValues.put("confirm_password", confirm_password);
        long insert = db.insert("contractor", null, contentValues);
        if (insert == -1)
            return false;
        else
            return true;
    }

    public Boolean checkEmail(String contractor_email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from contractor where contractor_email=?", new String[]{contractor_email});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    public Boolean emailPassword(String email, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from user where email=? and password=?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
