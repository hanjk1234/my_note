package com.framework_technology.esper.javabean;

import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-30
 * Time: 16:37
 */
public class Banana {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Banana.class);

    public static final String CLASSNAME = Banana.class.getName();
    private String id; //id
    private int price; //价格
    private double discount;//折扣

    Banana(int price, double discount) {
        this.price = price;
        this.discount = discount;
    }

    /**
     * @return 返回一个随机创建的 {@link Banana}
     */
    protected static Banana getRandomBanana() {
        Random random = new Random();
        Banana banana = new Banana(random.nextInt(10000), random.nextDouble());
        LOGGER.info("random Banana JavaBean is <{}>", banana);
        return banana;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * @return Banana 折扣后的价格
     * @see Banana#getPriceByDiscount(int, double)
     */
    public double getPriceByDiscount() {
        return this.price * this.discount;
    }

    /**
     * @param price
     * @param discount
     * @return Banana 折扣后的价格
     */
    public double getPriceByDiscount(int price, double discount) {
        return price * discount;
    }

    /**
     * 静态方法
     *
     * @param price
     * @param discount
     * @return Banana 折扣后的价格
     */
    public static double getPriceByDiscount2StaticMethod(int price, double discount) {
        return price * discount;
    }


}
