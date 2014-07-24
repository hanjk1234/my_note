package com.java.thread.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by lw on 14-7-22.
 */
public class DeliveryReportEventHandler implements EventHandler<ValueEvent> {

    /**
     * @param event
     * @param sequence   事件正在处理
     * @param endOfBatch 是否是最后一个事件在处理
     * @throws Exception
     */
    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {
        Thread.sleep(2000);
        System.out.println("event:\t" + event.getValue() + "\tsequence:\t" + sequence + "\tendOfBatch:\t" + endOfBatch);
    }
}
