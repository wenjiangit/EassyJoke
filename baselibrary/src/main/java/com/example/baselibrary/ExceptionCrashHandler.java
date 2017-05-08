package com.example.baselibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 全局的异常捕捉类
 * 单例
 * Created by wenjian on 2017/4/30.
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "ExceptionCrashHandler";

    private static final String KEY_CRASH_FILENAME = "CRASH_FILENAME";

    private static final String KEY_SP_NAME = "Crash";

    private static ExceptionCrashHandler sInstance;

    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private ExceptionCrashHandler() {
    }

    public static ExceptionCrashHandler getInstance() {
        if (sInstance == null) {
            //防止并发产生多个实例
            synchronized (ExceptionCrashHandler.class) {
                if (sInstance == null) {
                    sInstance = new ExceptionCrashHandler();
                }
            }
        }
        return sInstance;
    }


    public void init(Context context) {
        this.mContext = context;
        //设置应用的异常捕捉类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);

        //获取系统默认的异常捕捉类
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    }



    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e(TAG, "报异常了~",e);

        //保存到手机
        String crashFileName = saveInfoToSD(e);

        Log.d(TAG, "保存崩溃日志到本地文件:" + crashFileName);

        cacheCrashFile(crashFileName);

        //让系统默认的捕捉类去处理
        mDefaultUncaughtExceptionHandler.uncaughtException(t, e);

    }

    private void cacheCrashFile(String crashFileName) {
        SharedPreferences preferences = mContext.getSharedPreferences(KEY_SP_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(KEY_CRASH_FILENAME, crashFileName).apply();
    }

    /**
     * 获取崩溃文件
     * @return
     */
    public File getCrashFile() {
        SharedPreferences preferences = mContext.getSharedPreferences(KEY_SP_NAME, Context.MODE_PRIVATE);
        String fileName = preferences.getString(KEY_CRASH_FILENAME, "");
        return new File(fileName);
    }

    /**
     * 保存崩溃信息到本地
     * @param e
     * @return
     */
    private String saveInfoToSD(Throwable e) {
        StringBuilder stringBuilder = new StringBuilder();
        String fileName = null;

        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key).append(" = ").append(value).append("\n");
        }

        stringBuilder.append(obtainExceptionInfo(e));

//        Log.d(TAG, "saveInfoToSD: " + stringBuilder.toString());

        //有内部存储
        if (!Environment.getExternalStorageState().equals( Environment.MEDIA_UNMOUNTED)) {
            File crashDir = new File(mContext.getFilesDir() + File.separator + "crash");
            if (crashDir.exists()) {
                deleteDir(crashDir);
            }

            if (!crashDir.exists()) {
                crashDir.mkdir();
            }

            fileName = crashDir.toString()
                    + File.separator
                    + getAssignTime("yyyy_MM_dd_HH_mm")
                    + ".txt";
            try {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(stringBuilder.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 获取当前时间以特定的格式
     * @param template
     * @return
     */
    private String getAssignTime(String template) {
        DateFormat dateFormat = new SimpleDateFormat(template);
        String format = dateFormat.format(new Date());
        return format;
    }

    /**
     *
     * 获取异常信息
     * @param e
     * @return
     */
    private String obtainExceptionInfo(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        writer.close();
        return stringWriter.toString();
    }


    /**
     * 获取手机和应用信息
     * @param context
     * @return
     */
    private Map<String,String> obtainSimpleInfo(Context context) {
        Map<String, String> map = new HashMap<>();
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            map.put("packageName", info.packageName);
            map.put("versionName", info.versionName);
            map.put("versionCode", String.valueOf(info.versionCode));
            map.put("MODEL", Build.MODEL);
            map.put("FINGERPRINT", Build.FINGERPRINT);
            map.put("MANUFACTURER", Build.MANUFACTURER);
            map.put("VERSION.RELEASE", Build.VERSION.RELEASE);
            map.put("SDK_INT", String.valueOf(Build.VERSION.SDK_INT));
            return map;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                file.delete();
            }
        }
    }


}
