package com.example.baselibrary.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by douliu on 2017/5/25.
 */

public interface IDatabaseGenerator {

    SQLiteDatabase get(String dbName);
}
