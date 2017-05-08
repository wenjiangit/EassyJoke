package com.example.wenjian.eassyjoke;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.example.baselibrary.ExceptionCrashHandler;
import com.example.baselibrary.utils.Utils;

/**
 * Created by wenjian on 2017/4/30.
 */

public class BaseApplication extends Application {

    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        ExceptionCrashHandler.getInstance().init(this);

        Utils.init(this);
      /*  mPatchManager = new PatchManager(this);
        mPatchManager.init(getVersionName());
        mPatchManager.loadPatch();*/

    }

    private String getVersionName() {
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
