package com.framework_technology.esper.epl_context;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.LoggerFactory;


/**
 * 监听{@link com.framework_technology.esper.javabean.Apple}
 */
class AppleListener implements UpdateListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AppleListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null) {
            for (int i = 0; i < newEvents.length; i++) {
                //LOGGER.error("~~~~~~~~ newEvents‘s size is <{}> ~~~~~~~~", i);
                EventBean eventBean = newEvents[i];
                String key = "key1";
                String[] keys = new String[]{"key1", "key2", "name", "id"};
                LOGGER.info("Apple's " + key + " is <{}>, key2 is <{}> ,name is <{}>. id is <{}>", eventBean.get(keys[0]),eventBean.get(keys[1]),eventBean.get(keys[2]),eventBean.get(keys[3]));
            }
        }

        if (oldEvents != null) {
            LOGGER.error("~~~~~~~~ oldEvents is not null ! ~~~~~~~~");
        }
    }

}
