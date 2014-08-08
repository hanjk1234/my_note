package com.util.date;

import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by lw on 14-4-18.
 * <p>
 * 参考博客 <a>http://blog.csdn.net/xiaohulunb/article/details/21102055</a>
 */
public class Joda_Time {

    //定义日期-时间-周 的格式化字符串,lssues——月份为MM大写，小写默认为分钟数
    private static final String DATE = "yyyy-MM-dd";
    private static final String TIME = "HH:mm:ss";
    private static final String WEEK = "E";

    /**
     * 自定义DateTime
     *
     * @param year         year
     * @param month        month
     * @param day          day
     * @param hour         hour
     * @param minute       minute
     * @param second       second
     * @param milliseconds milliseconds
     * @return DateTime
     */
    public static DateTime getDareTime(int year, int month, int day, int hour, int minute, int second, int milliseconds) {
        return new DateTime(year, month, day, hour, minute, second, milliseconds);
    }

    /**
     * 获取当前星期，week
     *
     * @return String
     */
    public static String getNowWeek() {
        return new DateTime().toString(WEEK);
    }

    /**
     * 获取当前日期，Date
     *
     * @return String
     */
    public static String getNowDate() {
        return new DateTime().toString(DATE);
    }

    /**
     * 获取当前时间，Time
     *
     * @return String
     */
    public static String getNowTime() {
        return new DateTime().toString(DATE + " " + TIME);
    }

    /**
     * 获取当前时间，Time
     *
     * @param dateTime dateTime
     * @return String
     */
    public static String getDateTime(DateTime dateTime) {
        return dateTime.toString(DATE + " " + TIME);
    }


    /**
     * 根据多个参数自己组建日期格式，默认不含空格
     *
     * @param parameter parameter
     * @return String
     */
    public static String getTimeForParameter(String parameter) {
        return new DateTime().toString(parameter);
    }

    /**
     * 字符串转Date日期
     *
     * @param dataStr 默认格式为 yyyy-mm-dd
     * @return Date
     */
    public static Date str2Date(String dataStr) {

        DateTime dateTime = new DateTime(dataStr);
        return dateTime.toDate();
    }

    /**
     * 计算五年后的第二个月的最后一天-demo
     * monthOfYear()     // get monthOfYear property
     *
     * @param dateTime dateTime
     * @return String
     */
    public static String computationDateDemo(DateTime dateTime) {
        dateTime = dateTime.plusYears(5).monthOfYear().setCopy(2).dayOfMonth().withMaximumValue();
        return getDateTime(dateTime);
    }


    /**
     * other demo
     *
     * @return String
     */
    public static String computationDate() {
        DateTime dateTime = new DateTime();
        //日期 +-
        dateTime = dateTime.plusDays(1);
        //日期比较
        dateTime.isAfterNow();
        dateTime.isBefore(dateTime.getMillis());
        //获取当前分钟数of这个小时
        dateTime.getMinuteOfHour();
        //与 jdk日期 互通
        Date jdkDate = new Date();
        dateTime = new DateTime(jdkDate);

        return getDateTime(dateTime);
    }

    public static void main(String[] args) {

        Long aLong = (long) (1401785160 * 1000);
        DateTime dateTime = new DateTime(aLong);
        String s = getDateTime(dateTime);
        System.out.println(s);
    }
}
