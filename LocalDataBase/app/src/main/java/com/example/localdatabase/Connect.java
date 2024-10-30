package com.example.localdatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Connect extends SQLiteOpenHelper {
    public Connect(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Variables.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Variables.DELETE_TABLE);
        onCreate(db);
    }

    public int getLastInsertedId() {
        SQLiteDatabase db = this.getReadableDatabase();
        int lastId = -1; // Default value if no ID is found

        // Query to get the max ID from the table
        Cursor cursor = db.rawQuery("SELECT MAX(" + Variables.CAMPO_ID + ") FROM " + Variables.NAME_TABLE, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastId = cursor.getInt(0); // Retrieve the max ID
            cursor.close();
        }

        return lastId;
    }

    public int getRowCount(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return count;
    }
}
