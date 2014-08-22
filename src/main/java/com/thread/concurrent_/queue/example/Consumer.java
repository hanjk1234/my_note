package com.thread.concurrent_.queue.example;

import java.util.Random;

/**
 * 消费者
 *
 * @author wei.Li by 14-8-21.
 */
public class Consumer implements Runnable {

    @Override
    public void run() {
        while (MarketStorage.isRun_Cousumer) {

            try {
                //随机睡眠
                Thread.sleep(new Random().nextInt(MarketStorage.CONSUMER_THREAD_SLEEP));

                //消费对象
                CommodityObj commodityObj = MarketStorage.blockingQueue.take();
                System.out.println(this + " consumer obj ->" + commodityObj);
                MarketStorage.incConsumer_Obj_Count();//计数器++
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
