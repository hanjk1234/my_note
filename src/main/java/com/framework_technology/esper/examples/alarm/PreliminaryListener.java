package com.framework_technology.esper.examples.alarm;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.LoggerFactory;

/**
 * 初步过滤监听
 *
 * @author wei.Li by 14-8-14.
 */
public class PreliminaryListener implements UpdateListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PreliminaryListener.class);

    //合并处理
    private EsperService.EsperAlarmProvider mergeCepProvider;

    public PreliminaryListener(EsperService.EsperAlarmProvider mergeCepProvider) {
        this.mergeCepProvider = mergeCepProvider;
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {

        if (newEvents != null) {
            for (int i = 0; i < newEvents.length; i++) {
                LOGGER.debug("PreliminaryListener eventBean[{}] : <{}>", i, newEvents[i].getUnderlying());
                //过滤以后的事件发送到合并处理监听
                mergeCepProvider.sendEvent(AlarmData.getRandom());
                //mergeCepProvider.sendEvent(newEvents[i].getUnderlying());
            }
        }
    }
}
