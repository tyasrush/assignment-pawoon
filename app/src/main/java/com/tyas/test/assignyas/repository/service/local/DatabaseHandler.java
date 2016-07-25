package com.tyas.test.assignyas.repository.service.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tyas.test.assignyas.repository.entity.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyas on 7/23/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "assignment_test";

    // Title table name
    private static final String TABLE_TITLE = "title";

    // Title Table Columns names
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TITLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_BODY + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLE);
        onCreate(db);
    }

    public void addData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, data.getId());
        values.put(KEY_USER_ID, data.getUserId());
        values.put(KEY_TITLE, data.getTitle());
        values.put(KEY_BODY, data.getBody());

        // Inserting Row
        db.insert(TABLE_TITLE, null, values);
        db.close();
    }


    Data getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TITLE, new String[] { KEY_ID, KEY_USER_ID,
                        KEY_TITLE, KEY_BODY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Data data = new Data();
        data.setId(Integer.parseInt(cursor.getString(0)));
        data.setUserId(Integer.parseInt(cursor.getString(1)));
        data.setTitle(cursor.getString(2));
        data.setBody(cursor.getString(3));
        return data;
    }

    // Getting All Contacts
    public void getAllData(SQLiteLoadPattern sqLiteLoadPattern) {
        List<Data> datas = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TITLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setUserId(Integer.parseInt(cursor.getString(1)));
                data.setTitle(cursor.getString(2));
                data.setBody(cursor.getString(3));
                // Adding contact to list
                datas.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        if (datas != null) {
            if (sqLiteLoadPattern != null) {
                sqLiteLoadPattern.onLoadSuccess(datas);
            }
        } else {
            if (sqLiteLoadPattern != null) {
                sqLiteLoadPattern.onLoadFailed("Data null");
            }
        }
    }

    // Deleting single contact
    public void deleteData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TITLE, KEY_ID + " = ?",
                new String[] { String.valueOf(data.getId()) });
        db.close();
    }

    public interface SQLiteLoadPattern {
        void onLoadSuccess(List<Data> datas);
        void onLoadFailed(String message);
    }
}
