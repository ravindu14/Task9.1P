package com.example.lostfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "appDatabase", null, 21);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists lostItems (id integer primary key autoincrement, name text, phone text, description text, date text, location text, status text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists lostItems");
        onCreate(db);
    }

    public void insertItem(DatabaseItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", item.getName());
        contentValues.put("phone", item.getPhone());
        contentValues.put("description", item.getDescription());
        contentValues.put("date", item.getDate());
        contentValues.put("location", item.getLocation());
        contentValues.put("status", item.getStatus());

        long myTable = db.insert("lostItems", null, contentValues);
        Log.e(TAG, "insertData"+ myTable);
    }

    public ArrayList<ItemModel> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from lostItems", null, null);

        ArrayList<ItemModel> items = new ArrayList<>();

        while (cursor.moveToNext()) {
            ItemModel item = new ItemModel();

            item.id = cursor.getInt(0);
            item.name = cursor.getString(1);
            item.phone = cursor.getString(2);
            item.description = cursor.getString(3);
            item.date = cursor.getString(4);
            item.location = cursor.getString(5);
            item.status = cursor.getString(6);

            items.add(item);
        }

        return items;
    }

    public ItemModel getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from lostItems where id ="+id, null, null);

        System.out.println(cursor);
        ItemModel item = new ItemModel();

        while (cursor.moveToNext()) {
            item.id = cursor.getInt(0);
            item.name = cursor.getString(1);
            item.phone = cursor.getString(2);
            item.description = cursor.getString(3);
            item.date = cursor.getString(4);
            item.location = cursor.getString(5);
            item.status = cursor.getString(6);
        }

        return item;
    }

    public void removeItem (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("delete from lostItems where id ="+id, null, null);

        while (cursor.moveToNext()) {
            System.out.println("Record" + id + "deleted");
        }
    }
}
