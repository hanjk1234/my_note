package com.designmodel.singleton;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lw on 14-4-30.
 */
public class Test extends Thread implements Runnable {

    private static final String[] classNames = {"com.designmodel.singleton.Singleton", "com.designmodel.singleton.LazilySingleton", "com.designmodel.singleton.ResultSingleton"};
    //private static Test test = new Test();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Test().start();
        }
    }

    public void run() {
        try {
            System.out.println(this.toString() + "Thread run ....");
            Thread.sleep((int) (Math.random() * 1000));
            getTimes(classNames);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getTimes(String[] classNames) {
        for (String className : classNames) {

            long start = System.currentTimeMillis();
            Class c = null;
            try {
                c = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Method method = null;
            try {
                method = c.getMethod("getSingleton");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                method.invoke(c);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            System.out.println(className + "单例获取耗时-》" + (System.currentTimeMillis() - start) + "ms");
        }
        System.out.println();
    }
}
