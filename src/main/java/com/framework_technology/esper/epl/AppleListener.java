package com.framework_technology.esper.epl;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.LoggerFactory;


/**
 * 监听{@link com.framework_technology.esper.epl.Apple}
 */
class AppleListener implements UpdateListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AppleListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null) {
            for (int i = 0; i < newEvents.length; i++) {
                //LOGGER.error("~~~~~~~~ newEvents‘s size is <{}> ~~~~~~~~", i);
                EventBean eventBean = newEvents[i];
                String key = "aPrice";
                String color = "color", size = "size";
                LOGGER.info("Apple's" + key + " is <{}>,color  is <{}> ", eventBean.get(key), eventBean.get(color));
            }
        }

        if (oldEvents != null) {
            LOGGER.error("~~~~~~~~ oldEvents is not null ! ~~~~~~~~");
        }
    }

}
