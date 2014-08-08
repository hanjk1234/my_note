package com.thread.concurrent_;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-6
 * Time: 17:06
 */
public class Volatile_ {
    static final int NUM = 100;
    static int i = 0, j = 0;
    static volatile int m = 0, n = 0 ;

    static void add() {
        while (i <= 10000000) {
            i++;
            j++;
            m++;
            n = NUM + 1;
        }
    }

    static void outPut() {
        System.out.println("i=" + i + "\tj=" + j + "\tm=" + m + "\tn=" + n);
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        // final VolatileTest test = new VolatileTest();

        for (int k = 0; k < 10; k++) {

            new Thread() {
                public void run() {
                    Volatile_.add();
                }
            }.start();

            new Thread() {
                public void run() {
                    Volatile_.outPut();
                }
            }.start();
        }
    }
}
/**
 * 输出结果：
 * i=559415 j=560994
 * i=42344  j=42511
 */

/**
 * 一些线程执行add方法，另一些线程执行outPut方法，outPut方法有可能打印出不同的i和j的值，按照之前分析的线程执行过程分析一下：

 1. 将变量i从主内存拷贝到工作内存；
 2. 改变i的值；
 3. 刷新主内存数据；
 4. 将变量j从主内存拷贝到工作内存；
 5. 改变j的值；
 6. 刷新主内存数据；

 */

/**
 * 加上volatile可以将共享变量m和n的改变直接响应到主内存中，这样保证了m和n的值可以保持一致，
 * 然而我们不能保证执行outPut方法的线程是在i和j执行到什么程度获取到的，
 * 所以volatile可以保证内存可见性，不能保证并发有序性，因此在上面的输出结果中，m和n的值是不一样的。
 *
 * 在使用volatile关键字时要慎重，并不是只要简单类型变量使用volatile修饰，对这个变量的所有操作都是原来操作，
 * 当变量的值由自身的上一个决定时，如n=n+1、n++等，volatile关键字将失效，只有当变量的值和自身上一个值无关时对该变量的操作才是原子级别的，
 * 如n = m + 1，这个就是原级别的。所以在使用volatile关键时一定要谨慎，如果自己没有把握，可以使用synchronized来代替volatile。
 */
