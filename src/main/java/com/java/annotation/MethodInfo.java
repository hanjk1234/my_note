package com.java.annotation;

import java.lang.annotation.*;

/**
 * Created by lw on 14-5-30.
 * 自定义注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo {
    public String Value() default "暂无说明";
}
/*
四个定义注解时候的限定数据

@Target(ElementType.TYPE)           :接口、类、枚举、注解
@Target(ElementType.FIELD)          :字段、枚举的常量
@Target(ElementType.METHOD)         :方法
@Target(ElementType.PARAMETER)      :方法参数
@Target(ElementType.CONSTRUCTOR)    :构造函数
@Target(ElementType.LOCAL_VARIABLE) :局部变量
@Target(ElementType.ANNOTATION_TYPE):注解
@Target(ElementType.PACKAGE)        :包

@Retention(RetentionPolicy.SOURCE)  :在源文件中有效（即源文件保留）
@Retention(RetentionPolicy.CLASS)   :在class文件中有效（即class保留）
@Retention(RetentionPolicy.RUNTIME) :在运行时有效（即运行时保留）

@Inherited：说明子类可以继承父类中的该注解

@Document：说明该注解将被包含在javadoc中
*/
