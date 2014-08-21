package com.thread.jdk_thread.example;

import java.util.Random;

/**
 * 消费者
 *
 * @author wei.Li by 14-8-21.
 */
public class Consumer implements Runnable {


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(MarketFactory.CONSUMER_THREAD_SLEEP));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                //消费对象
                Obj obj = MarketFactory.blockingQueue.take();
                System.out.println(this + " consumer obj ->" + obj);
                MarketFactory.incConsumer_Obj_Count();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
