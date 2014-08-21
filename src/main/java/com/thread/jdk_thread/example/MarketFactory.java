package com.thread.jdk_thread.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wei.Li by 14-8-21.
 */
public class MarketFactory {

    //生产者线程池
    protected static final ExecutorService EXECUTOR_SERVICE_PRODUCER
            = Executors.newFixedThreadPool(10);
    //启动生产者线程数量
    public static final int PRODUCER_THREAD_NUM = 2;
    //生产者线程睡眠随机最大时间
    public static final int PRODUCER_THREAD_SLEEP = 200;
    //生产者生成对象次数
    public static int PRODUCER_OBJ_COUNT = 0;

    //消费者线程池
    protected static final ExecutorService EXECUTOR_SERVICE_CONSUMER
            = Executors.newFixedThreadPool(10);
    //启动消费者线程数量
    public static final int CONSUMER_THREAD_NUM = 20;
    //消费者线程睡眠随机最大时间
    public static final int CONSUMER_THREAD_SLEEP = 1000;
    //消费者消费对象次数
    public static int CONSUMER_OBJ_COUNT = 0;

    //存储数据的队列
    protected static LinkedBlockingQueue<Obj> blockingQueue = new LinkedBlockingQueue<Obj>();

    /**
     * 生产者线程生成
     */
    private static void runProducer() {
        for (int i = 0; i < PRODUCER_THREAD_NUM; i++) {
            EXECUTOR_SERVICE_PRODUCER.submit(new Producer());
        }
    }

    /**
     * 消费者线程生成
     */
    private static void runConsumer() {
        for (int i = 0; i < CONSUMER_THREAD_NUM; i++) {
            Thread thread = new Thread(new Consumer());
            EXECUTOR_SERVICE_CONSUMER.submit(thread);
        }
    }

    /**
     * 关闭线程池
     */
    private static void shumdown() {
        if (!EXECUTOR_SERVICE_PRODUCER.isShutdown()) {
            EXECUTOR_SERVICE_PRODUCER.shutdown();
        }
        if (!EXECUTOR_SERVICE_CONSUMER.isShutdown()) {
            EXECUTOR_SERVICE_CONSUMER.shutdown();
        }
    }

    /**
     * 计数器+1
     */
    protected static synchronized void incProducer_Obj_Count() {
        PRODUCER_OBJ_COUNT++;
        System.out.println("PRODUCER_OBJ_COUNT->" + PRODUCER_OBJ_COUNT);
    }

    /**
     * 计数器+1
     */
    protected static synchronized void incConsumer_Obj_Count() {
        CONSUMER_OBJ_COUNT++;
        System.out.println("CONSUMER_OBJ_COUNT->" + CONSUMER_OBJ_COUNT);
    }


    public static void main(String[] args) {
        runConsumer();
        runProducer();
    }
}
