package com.example.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wenjian on 2017/4/29.
 */

//@Target(ElementType.FIELD) 适用位置:方法声明
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)  //什么时候生效:运行时
public @interface ViewInject {
    int value();
}
