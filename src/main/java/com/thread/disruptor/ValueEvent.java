package com.thread.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by lw on 14-7-3.
 * <p/>
 * 定义 ValueEvent 类，该类作为填充 RingBuffer 的消息，
 * 生产者向该消息中填充数据（就是修改 value 属性值，后文用生产消息代替），
 * 消费者从消息体中获取数据（获取 value 值，后文用消费消息代替）
 *
 * @see com.thread.disruptor.DeliveryReportEventHandler
 */
public final class ValueEvent extends DeliveryReportEventHandler {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public final static EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>() {
        public ValueEvent newInstance() {
            return new ValueEvent();
        }
    };

}
