package com.thread.jdk_thread;

import com.util.date.Joda_Time;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: lw
 * Date: 14-7-1
 */
public class FixedThreadPool_Example {

    static ExecutorService executor = Executors.newFixedThreadPool(10);

    private static void run2FixedThreadPool(String task) {
        // executor.execute(new FixedThreadPool_Handle());
        FutureTask<String> future;
        future = new FutureTask<String>(new Callable<String>() {//使用Callable接口作为构造参数
            public String call() {
                try {
                    //模拟执行时间为随机值
                    Thread.sleep(new Random().nextInt(4000));
                    System.out.println("run task :" + task);
                    System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型
                return Joda_Time.getNowTime();
            }
        });
        //executor.submit(future);
        executor.execute(future);
/*
        try {
            //接受任务返回，可以设置超时
            System.out.println(future.get(3, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/
    }

    public static void main(String[] args) {
        String task = "";//模拟任务
        for (int i = 0; i < 30; i++) {
            run2FixedThreadPool(i + task);
        }
        System.out.println("---------- add task done ----------");
    }
}

class FixedThreadPool_Handle implements Runnable {

    @Override
    public void run() {
        System.out.println(this.toString());
    }
}

