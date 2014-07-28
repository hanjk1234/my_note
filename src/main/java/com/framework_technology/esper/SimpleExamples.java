package com.framework_technology.esper;


import com.espertech.esper.client.*;

import java.util.UUID;

/**
 * Created by lw on 14-7-24.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 目标：计算三个苹果的平均价格
 * <p>
 * apple javaBean
 */
class Apple {
    protected static final String AVG_PRICE = "avg(price)";
    protected static final String CLASSNAME = Apple.class.getName();
    private String id;
    private int price;

    Apple(String id, int price) {
        this.id = id;
        this.price = price;
    }

    Apple(int price) {
        this.id = UUID.randomUUID().toString();
        this.price = price;
    }

    public String getId() {
        return id;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

/**
 * 监听{@link Apple}
 */
class AppleListener implements UpdateListener {

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null) {
            Double avg = (Double) newEvents[0].get(Apple.AVG_PRICE);
            System.out.println("Apple's average price is \t" + avg);
        }
    }

}

/**
 * main 函数测试
 */
public class SimpleExamples {

    public static void main(String[] args) throws InterruptedException {
        EPServiceProvider defaultProvider = EPServiceProviderManager.getDefaultProvider();
        EPAdministrator epAdministrator = defaultProvider.getEPAdministrator();
        EPRuntime epRuntime = defaultProvider.getEPRuntime();

        String epl = "select " + Apple.AVG_PRICE + " from " + Apple.CLASSNAME + ".win:length_batch(3)";

        EPStatement epStatement = epAdministrator.createEPL(epl);

        epStatement.addListener(new AppleListener());

        Apple apple1 = new Apple(50);
        epRuntime.sendEvent(apple1);

        Apple apple2 = new Apple(1);
        epRuntime.sendEvent(apple2);

        Apple apple3 = new Apple(2);
        epRuntime.sendEvent(apple3);
    }
}
