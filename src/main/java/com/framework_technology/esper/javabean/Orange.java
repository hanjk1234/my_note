package com.framework_technology.esper.javabean;

import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 *  @author wei.Li by 14-8-14.
 */
public class Orange {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Orange.class);
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.CHINA);

    private static final String[] COLORS = new String[]{"1", "2", "3"};
    private static final int COLORS_LENGTH = COLORS.length;
    public static final String AVG_PRICE = "avg(price)";
    public static final String CLASSNAME = Orange.class.getName();

    private String id; //id
    private int price; //价格
    private double discount;//折扣
    private String color;//颜色 COLORS 中随机获取
    private int size;//大小  1-10
    private Long create_time;//创建时间

    Orange() {
        Random random = new Random();
        this.id = random.nextInt(5) + "";
        this.price = random.nextInt(10);
        this.discount = random.nextDouble();
        this.color = COLORS[random.nextInt(COLORS_LENGTH)];
        this.size = random.nextInt(2);
        this.create_time = System.currentTimeMillis() - random.nextInt(3) * 1000L;
    }

    /**
     * @return 返回一个随机创建的 {@link com.framework_technology.esper.javabean.Apple}
     */
    public static Orange getRandomOrange() {

        Orange orange = new Orange();
        LOGGER.info("~~~~~~~~~ random Orange JavaBean is <{}> ~~~~~~~~~~", orange);
        return orange;
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

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    /**
     * @return 该Orange 折扣后的价格
     * @see com.framework_technology.esper.javabean.Apple#getPriceByDiscount(int, double)
     */
    public double getPriceByDiscount() {
        return this.price * this.discount;
    }

    /**
     * @param price    price
     * @param discount discount
     * @return 该Orange 折扣后的价格
     */
    public double getPriceByDiscount(int price, double discount) {
        return price * discount;
    }

    /**
     * 静态方法
     *
     * @param price    price
     * @param discount discount
     * @return 该Orange 折扣后的价格
     */
    public static double getPriceByDiscount2StaticMethod(int price, double discount) {
        return price * discount;
    }

    @Override
    public String toString() {
        return "Orange{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", create_time=" + FORMAT.format(new Date(create_time)) +
                '}';
    }
}
