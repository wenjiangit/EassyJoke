package com.example.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by wenjian on 2017/4/29.
 */

public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity),activity);

    }

    public static void inject(View view) {
        inject(new ViewFinder(view),view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view),object);
    }

    private static void inject(ViewFinder finder, Object object) {
        injectField(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 注入点击事件
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        Class<?> clazz = object.getClass();
        //获取所有的方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //获取OnClick注解
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                //获取view的id
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    //找到对应的view
                    View view = finder.findViewById(viewId);
                    //检查是否有CheckNet注解
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;

                    if (view != null) {
                        view.setOnClickListener(new DeclaredOnClickListener(method,object,isCheckNet));
                    }
                }
            }
        }
    }


    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Method mMethod;
        private Object mObject;
        private boolean mIsCheckNet;


        public DeclaredOnClickListener(Method method, Object object,boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {

            if (mIsCheckNet) {
                if (!networkAvailable(v.getContext())) {
                    Toast.makeText(v.getContext(), "亲,您的网络不太给力!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            try {
                mMethod.setAccessible(true);
                //反射调用有一个参数的方法
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    //发生异常则调用无参的方法
                    mMethod.invoke(mObject, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * 检测是否有网
     * @param context
     * @return
     */
    public static boolean networkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 注入属性
     * @param finder
     * @param object
     */
    private static void injectField(ViewFinder finder, Object object) {
        //1.获取class
        Class<?> clazz = object.getClass();

        //2.获取所有的属性
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            ViewInject viewById = field.getAnnotation(ViewInject.class);
            if (viewById != null) {
                int viewId = viewById.value();
                View view = finder.findViewById(viewId);
                if (view != null) {
                    //设置可以访问所有修饰符  包括private
                    field.setAccessible(true);
                    try {
                        //注入属性
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
