package com.framework_technology.esper.javabean;

import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * apple javaBean
 */
public class Apple {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Apple.class);

    private static final String[] COLORS = new String[]{"1", "2", "3"};
    private static final int COLORS_LENGTH = COLORS.length;
    public static final String AVG_PRICE = "avg(price)";
    public static final String CLASSNAME = Apple.class.getName();

    private String id; //id
    private int price; //价格
    private double discount;//折扣
    private String color;//颜色 COLORS 中随机获取
    private int size;//大小  1-10

    Apple() {
        Random random = new Random();
        this.id = "2";
        this.price = random.nextInt(3);
        this.discount = random.nextDouble();
        this.color = COLORS[random.nextInt(COLORS_LENGTH)];
        this.size = random.nextInt(10);
    }

    /**
     * @return 返回一个随机创建的 {@link Apple}
     */
    public static Apple getRandomApple() {

        Apple apple = new Apple();
        LOGGER.info("~~~~~~~~~ random Apple JavaBean is <{}> ~~~~~~~~~~", apple);
        return apple;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return 该Apple 折扣后的价格
     * @see Apple#getPriceByDiscount(int, double)
     */
    public double getPriceByDiscount() {
        return this.price * this.discount;
    }

    /**
     * @param price
     * @param discount
     * @return 该Apple 折扣后的价格
     */
    public double getPriceByDiscount(int price, double discount) {
        return price * discount;
    }

    /**
     * 静态方法
     *
     * @param price
     * @param discount
     * @return 该Apple 折扣后的价格
     */
    public static double getPriceByDiscount2StaticMethod(int price, double discount) {
        return price * discount;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", color='" + color + '\'' +
                ", size=" + size +
                '}';
    }
}
