package com.framework_technology.esper.views;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.LoggerFactory;


/**
 * 监听{@link com.framework_technology.esper.javabean.Apple}
 */
class ViewsAppleListener implements UpdateListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ViewsAppleListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        String key = "price";
        if (newEvents != null) {
            for (int i = 0; i < newEvents.length; i++) {
                //LOGGER.error("~~~~~~~~ newEvents‘s size is <{}> ~~~~~~~~", i);
                EventBean eventBean = newEvents[i];
                System.out.println(" newEvents[" + i + "]  " + key + " is: " + eventBean.get(key));
            }
        }

        System.out.println();
        if (oldEvents != null) {
            LOGGER.error("~~~~~~~~ oldEvents is not null ! ~~~~~~~~");
        }
    }

}
