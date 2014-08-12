package com.framework_technology.esper.epl;

import com.framework_technology.esper.javabean.Apple;
import com.framework_technology.esper.javabean.Banana;

/**
 * Subqueries子查询
 * <p>
 * 基本上与SQL 的写法类似。
 * <p>
 * API - 5.11. Subqueries
 *
 * @author wei.Li by 14-8-12.
 */
public class EPL_5_Subqueries {


    /**
     * 以下限制适用于子查询：
     * <p>
     * 1.子查询流定义必须定义一个数据窗口或其它视图，以限制子查询的结果，减少了保持子查询执行的事件数
     * 2.子查询只能从子句包含一个select子句，一个where子句和GROUP BY子句。 having子句，以及连接，外连接和输出速率限制在子查询中是不允许的。
     * 3.如果使用聚合函数在子查询时，请注意以下限制：
     * 3.1没有一个相关流（次）的性质可以在聚合函数中使用。
     * 3.2子选择流的特性都必须在聚合函数。
     *
     * @return epl[]
     */
    protected static String subqueries() {

        /**
         * 子查询结果作为外部事件的属性
         * Apple事件 的 id 与 当前窗口中的最后一个Banana 的price 作为结果集输出
         */
        String epl1 = "select id, (select price from " + Banana.CLASSNAME + ".std:lastevent()) as lastBanana_price from " + Apple.CLASSNAME;

        /**
         * 子查询关联外部事件的属性
         */
        String epl2 = "select * from " + Apple.CLASSNAME + " as Apple where 3 = " +
                "  (select price from " + Banana.CLASSNAME + ".std:unique(id) where id = Apple.id)";
        //每进入一个Apple 事件。查询 id 与当前窗口中Banana 按照std:unique(id)保留的所有唯一值，无一个匹配则输出 null
        String epl3 = "select id, (select price from " + Banana.CLASSNAME + ".std:unique(id) where id = apple.id) as price from " + Apple.CLASSNAME + " as apple";

        /**
         * 子查询内部事件作为外部事件的属性
         */
        String epl4 = "select (select * from " + Banana.CLASSNAME + ".std:lastevent()) as banana from " + Apple.CLASSNAME;

        /**
         * 子查询中应用聚合函数
         */
        String epl5 = "select * from " + Apple.CLASSNAME + " where price > (select max(price) from " + Banana.CLASSNAME + "(id='Banana_Id').std:lastevent())";
        return epl4;
    }


    /**
     * The 'exists' Keyword
     *
     * @return epl
     */
    protected static String exists() {
        String epl1 = "select * from " + Apple.CLASSNAME + " as RFID " +
                "  where exists (select * from " + Banana.CLASSNAME + ".std:unique(id) where id = RFID.id)";
        return epl1;
    }


    /**
     * The 'in' and 'not in' Keywords
     *
     * @return epl
     */
    protected static String in_notin() {
        String epl1 = "select * from " + Apple.CLASSNAME + " as RFID " +
                "  where RFID.id in (select id from " + Banana.CLASSNAME + ".std:unique(id))";
        return epl1;
    }


    /**
     * The 'any' and 'some' Keywords
     * any :至少大于结果集的一个 or 连接
     * some:至少等于结果集的一个 or 连接
     *
     * @return epl
     */
    protected static String any_some() {
        String epl1 = "select * from " + Apple.CLASSNAME + " as RFID " +
                "  where RFID.id < any(select id from " + Banana.CLASSNAME + ".win:keepall())";
        return epl1;
    }


    /**
     * The 'all' Keyword
     * all:必须小于所有结果集的值才满足 and 连接
     *
     * @return epl
     */
    protected static String all() {
        String epl1 = "select * from " + Apple.CLASSNAME + " as RFID " +
                "  where RFID.id < all(select id from " + Banana.CLASSNAME + ".win:keepall())";
        return epl1;
    }


}
