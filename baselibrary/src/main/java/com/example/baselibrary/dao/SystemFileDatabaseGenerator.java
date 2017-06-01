package com.example.baselibrary.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.baselibrary.utils.Utils;

/**
 * Created by douliu on 2017/5/25.
 */

public class SystemFileDatabaseGenerator implements IDatabaseGenerator{

    @Override
    public SQLiteDatabase get(String dbName) {
        MySqliteOpenHelper helper = new MySqliteOpenHelper(Utils.getContext(), dbName);
        return helper.getWritableDatabase();
    }


    public static class MySqliteOpenHelper extends SQLiteOpenHelper {
        public MySqliteOpenHelper(Context context, String name) {
            super(context, name, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

