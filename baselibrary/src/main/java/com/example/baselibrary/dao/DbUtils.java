package com.example.baselibrary.dao;

import java.lang.reflect.ParameterizedType;

/**
 * Created by douliu on 2017/5/24.
 */

public class DbUtils {


    public static String getColumnByType(String type) {
        if (type.equals("String")) {
            return " text";
        } else if (type.equals("int")) {
            return " int";
        } else if (type.equals("boolean")) {
            return " boolean";
        } else if (type.equals("float")) {
            return " float";
        } else if (type.equals("double")) {
            return " double";
        } else if (type.equals("char")) {
            return " varChar";
        }
        return "";
    }


    /**
     * 过滤非法的key
     * @param key
     * @return
     */
    public static boolean isUnValidKey(String key) {
        return "serialVersionUID".equals(key) ||"$change".equals(key);
    }


}
