package com.example.baselibrary.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by douliu on 2017/5/24.
 */

public class DaoSupport<T> implements IDaoSupport<T> {

    private static final String TAG = "DaoSupport";
    private SQLiteDatabase mSQLiteDatabase;

    private Class<T> mClazz;

    private String mDbName;

    private static final Map<Class, Method> mPutMethods
            = new ArrayMap<>();

    public DaoSupport(SQLiteDatabase SQLiteDatabase, Class<T> clazz) {
        mSQLiteDatabase = SQLiteDatabase;
        mClazz = clazz;
        mDbName = mClazz.getSimpleName();
        init();
    }

    @Override
    public long insert(T obj) {
        ContentValues values = new ContentValues();
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(obj);
                Log.d(TAG, "insert: key-->" + key + "  value-->" + value);
                if (value == null || DbUtils.isUnValidKey(key)) {//如果值为空,则跳过
                    continue;
                }
                Class<?> valueType = value.getClass();
                Method method = mPutMethods.get(valueType);
                if (method == null) {
                    method = ContentValues.class.getDeclaredMethod("put", String.class, valueType);
                    mPutMethods.put(valueType, method);
                }
                method.invoke(values, key, value);
            } catch (Exception e) {
                Log.e(TAG, "insert object error: ", e);
                return 0;
            }
        }
        return mSQLiteDatabase.insert(mDbName, null, values);
    }

    @Override
    public void insert(List<T> datas) {
        mSQLiteDatabase.beginTransaction();
        for (T data : datas) {
            insert(data);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    @Override
    public void delete() {
        mSQLiteDatabase.delete(mDbName, null, null);
    }

    @Override
    public List<T> queryAll() {
        Cursor cursor = mSQLiteDatabase.query(mDbName, null, null, null, null, null, null);
        List<T> result = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                try {
                    Field[] fields = mClazz.getDeclaredFields();
                    T t = mClazz.newInstance();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String name = field.getName();
                        int columnIndex = cursor.getColumnIndex(name);
                        if (columnIndex == -1) {
                            continue;
                        }
                        Object obj = invokeMethodByType(cursor, columnIndex);
                        if (obj != null) {
                            field.set(t, obj);
                        }
                    }
                    result.add(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private Object invokeMethodByType(Cursor cursor, int index) {
        int type = cursor.getType(index);
        if (type == Cursor.FIELD_TYPE_INTEGER) {
            return cursor.getInt(index);
        } else if (type == Cursor.FIELD_TYPE_STRING) {
            return cursor.getString(index);
        } else if (type == Cursor.FIELD_TYPE_FLOAT) {
            return cursor.getFloat(index);
        } else if (type == Cursor.FIELD_TYPE_BLOB) {
            return cursor.getBlob(index);
        }
        return null;
    }

    @Override
    public void update() {

    }

    private void init() {
        String sql = "create table if not exists ";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(mClazz.getSimpleName());
        sb.append(" (id integer primary key autoincrement, ");

        Field[] fields = mClazz.getDeclaredFields();
        Log.d(TAG, "fields: " + Arrays.toString(fields));
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getType().getSimpleName();
            if (DbUtils.isUnValidKey(name)) {
                continue;
            }

            Log.d(TAG, "name--> " + name + "  type-->" + type);
            sb.append(name).append(DbUtils.getColumnByType(type)).append(", ");
        }

        sb.replace(sb.length() - 2, sb.length(), ")");
        String sqlStr = sb.toString();
        Log.d(TAG, "sql语句: " + sqlStr);
        mSQLiteDatabase.execSQL(sqlStr);
    }

    /**
     * 获得泛型的class的方法
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Class getTypeClass() {
        Class clazz = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        return clazz;
    }
}
