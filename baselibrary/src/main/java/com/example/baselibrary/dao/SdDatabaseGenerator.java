package com.example.baselibrary.dao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by douliu on 2017/5/25.
 */

public class SdDatabaseGenerator implements IDatabaseGenerator {

    private static final String TAG = "SdDatabaseGenerator";

    @Override
    public SQLiteDatabase get(String dbName) {
        File dbDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath()+File.separator+"eassyjoke"+File.separator+"database");
        if (!dbDir.exists()) {
            if (!dbDir.mkdirs()) {
                throw new RuntimeException("数据库文件创建失败!请检查是否申请了权限");
            }
        }
        File file = new File(dbDir, dbName);
        Log.d(TAG, "数据库文件路径: " + file.getAbsolutePath());
        return SQLiteDatabase.openOrCreateDatabase(file, null);
    }


}
