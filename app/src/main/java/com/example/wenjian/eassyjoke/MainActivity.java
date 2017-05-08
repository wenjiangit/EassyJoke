package com.example.wenjian.eassyjoke;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baselibrary.ExceptionCrashHandler;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.dialog.MyAlertDialog;
import com.example.baselibrary.ioc.ViewInject;
import com.example.framelibrary.base.BaseSkinActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends BaseSkinActivity implements View.OnClickListener {

    @ViewInject(R.id.tv_test)
    private TextView mTvTest;

    @ViewInject(R.id.btn_test)
    private Button mBtnTest;


    @Override
    protected void initData() {

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        ,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

    private void uploadCrashInfo() {
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();

        Log.d(TAG, "uploadCrashInfo: " + crashFile);
        FileInputStream fis = null;
        if (crashFile.exists()) {
            try {

                fis = new FileInputStream(crashFile);
                byte [] buffer = new byte[1024];
                int len;
                StringBuilder stringBuilder = new StringBuilder();
                while ((len = fis.read(buffer)) != -1) {
                    String s = new String(buffer, 0, len);
                    stringBuilder.append(s);
                }
                // TODO: 2017/5/1 上传到服务器
                Log.e(TAG, "崩溃信息:" + stringBuilder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        if (fixFile.exists()) {
            try {
                BaseApplication.mPatchManager.addPatch(fixFile.getAbsolutePath());
                toast("修复成功");
            } catch (IOException e) {
                e.printStackTrace();
                toast("修复失败");
            }
        }
    }*/

    @Override
    protected void initView() {
        mTvTest.setText("hello ioc");

        mBtnTest.setOnClickListener(this);

        uploadCrashInfo();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        MyAlertDialog dialog = new MyAlertDialog.Builder(this)
                .setContentView(R.layout.layout_test)
                .fromBottom(true)
                .fullWidth()
                .show();
        final EditText editText = dialog.getView(R.id.et_comment);
        dialog.setOnClickListener(R.id.btn_send, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast(editText.getText().toString().trim());
            }
        });

    }
}
