package com.thread.jdk_thread.example;

import java.util.Random;

/**
 * 生产者
 *
 * @author wei.Li by 14-8-21.
 */
public class Producer implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(MarketFactory.PRODUCER_THREAD_SLEEP));

                //生成对象
                Obj obj = new Obj();
                boolean b = MarketFactory.blockingQueue.offer(obj);
                if (b) {
                    System.out.println(this + " producer obj succeed->" + obj);
                    MarketFactory.incProducer_Obj_Count();
                } else {
                    System.out.println(this + " producer obj failure->" + obj);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
