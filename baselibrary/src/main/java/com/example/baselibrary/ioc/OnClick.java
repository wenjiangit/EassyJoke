package com.example.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wenjian on 2017/4/29.
 */

@Target(ElementType.METHOD)         //用于注解方法
@Retention(RetentionPolicy.RUNTIME)     //运行时起作用
public @interface OnClick {

    int[] value();
}
