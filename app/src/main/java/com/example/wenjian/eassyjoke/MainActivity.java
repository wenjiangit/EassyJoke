package com.example.wenjian.eassyjoke;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.baselibrary.ExceptionCrashHandler;
import com.example.baselibrary.dao.DaoSupportFactory;
import com.example.baselibrary.dao.IDaoSupport;
import com.example.baselibrary.dialog.MyAlertDialog;
import com.example.baselibrary.ioc.ViewInject;
import com.example.framelibrary.base.BaseSkinActivity;
import com.example.framelibrary.base.DefaultNavigationBar;
import com.example.wenjian.eassyjoke.model.Person;
import com.example.wenjian.eassyjoke.test.ISubject;
import com.example.wenjian.eassyjoke.test.MyInvokeHandler;
import com.example.wenjian.eassyjoke.test.RealSubject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseSkinActivity implements View.OnClickListener {


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

        mBtnTest.setOnClickListener(this);

        uploadCrashInfo();
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setBack(false)
                .setTitle("主页")
                .setRightText("说明")
                .setRightClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(TestActivity.class);
                    }
                })
                .create();
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_main;
    }

    public void baseUse(View view) {
        startActivity(TestActivity.class);
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

        IDaoSupport<Person> support = DaoSupportFactory.getInstance().getDao(Person.class);
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Person person = new Person();
            person.setName("wenjian");
            person.setAge(22 + i);
            person.setMoney(20000 + i);
            persons.add(person);
        }
        support.insert(persons);

        List<Person> personList = support.queryAll();
        Log.d(TAG, "personList: " + personList);

    }
}
