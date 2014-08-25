package com.java8_;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-7
 * <p>
 * Lambda表达式教程
 */
public class Lambda_ {


    public static void main(String[] args) {
        /*HasDefauleMethodInterface hasDefauleMethod = (s, i) -> {
            System.out.println("s:" + s);
            System.out.println("i:" + i);
        };

        hasDefauleMethod.run_("1", 2);
        hasDefauleMethod.defaultMethod();*/
    }

    private static void forEach() {
        List<String> list = Lists.newArrayList("a", "b");
        //list.forEach(Lambda_::analogThreads);
    }

    private static void aVoid(String s) {
        System.out.println("analogThreads's s is " + s);
    }
}


@FunctionalInterface
interface HasDefauleMethodInterface {

    public void run_(String s, int i);

}