package com.thread.concurrent_.queue.example;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 市场演示仓库
 *
 * @author wei.Li by 14-8-21.
 */
public class MarketStorage {

    //生产者线程池
    protected static final ExecutorService EXECUTOR_SERVICE_PRODUCER
            = Executors.newFixedThreadPool(10);
    //启动生产者线程数量
    protected static final int PRODUCER_THREAD_NUM = 2;
    //生产者线程睡眠随机最大时间
    protected static final int PRODUCER_THREAD_SLEEP = 200;
    //生产者生成对象次数
    protected static int producerObj_Count = 0;
    //是否停止生产
    protected static boolean isRun_Producer = true;


    //消费者线程池
    protected static final ExecutorService EXECUTOR_SERVICE_CONSUMER
            = Executors.newFixedThreadPool(10);
    //启动消费者线程数量
    protected static final int CONSUMER_THREAD_NUM = 20;
    //消费者线程睡眠随机最大时间
    protected static final int CONSUMER_THREAD_SLEEP = 1000;
    //消费者消费对象次数
    protected static int consumerObj_Count = 0;
    //是否停止消费
    protected static boolean isRun_Cousumer = true;

    //市场仓库-存储数据的队列 默认仓库容量大小100
    /**
     * @see com.thread.concurrent_.queue.LinkedBlockingQueue_#linkedBlockingQueue2Void()
     */
    protected static LinkedBlockingQueue<CommodityObj> blockingQueue
            = new LinkedBlockingQueue<CommodityObj>(100);

    /**
     * 生成生产者线程
     */
    private static void runProducer() {
        for (int i = 0; i < PRODUCER_THREAD_NUM; i++) {
            EXECUTOR_SERVICE_PRODUCER.submit(new Producer());
        }
    }

    /**
     * 生成消费者线程生成
     */
    private static void runConsumer() {
        for (int i = 0; i < CONSUMER_THREAD_NUM; i++) {
            Thread thread = new Thread(new Consumer());
            EXECUTOR_SERVICE_CONSUMER.submit(thread);
        }
    }

    /**
     * 停止线程生产与消费
     * 关闭线程池
     */
    private static void shumdown() {
        if (!EXECUTOR_SERVICE_PRODUCER.isShutdown()) {
            isRun_Producer = false;
            EXECUTOR_SERVICE_PRODUCER.shutdown();
        }
        if (!EXECUTOR_SERVICE_CONSUMER.isShutdown()) {
            isRun_Cousumer = false;
            EXECUTOR_SERVICE_CONSUMER.shutdown();
        }
    }

    /**
     * 生产对象 - 计数器+1
     */
    protected static synchronized void incProducer_Obj_Count() {
        producerObj_Count++;
        System.out.println("producerObj_Count->" + producerObj_Count);
    }

    /**
     * 消费对象 - 计数器+1
     */
    protected static synchronized void incConsumer_Obj_Count() {
        consumerObj_Count++;
        System.out.println("consumerObj_Count->" + consumerObj_Count);
    }


    public static void main(String[] args) {
        runConsumer();
        runProducer();

        /**
         * 1 min 后停止执行
         */
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                shumdown();
                System.out.println("~~~~~~~~~~~~ shumdown done ~~~~~~~~~~~~~~");
            }
        }, 1000 * 6L);
    }
}
