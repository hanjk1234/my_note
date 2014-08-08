package com.framework_technology.esper.views;

import com.framework_technology.esper.javabean.Apple;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-8
 */
public class View_1 {


    /**
     * Table 12.1. Built-in Data Window Views
     * <p>
     * View	Syntax	                                             Description
     * ==========================================================================================================
     * win:length(size)	                                滑动窗口，存最近的 size 条数据
     * win:length_batch(size)	                        批处理-间隔窗口，存满 size 条触发查询
     * win:time(time period)	                        滑动窗口，存最近 size 秒内的数据
     * win:ext_timed(timestamp expression, time period)	滑动窗口，存最近 time period 内的数据，不再基于EPL引擎，而是系统时间戳[timestamp]
     * win:time_batch(time period[,optional reference point] [, flow control])	            批次事件和释放他们指定的时间间隔,与流控制选项
     * win:ext_timed_batch(timestamp expression, time period[,optional reference point])	批处理事件并释放它们的基础上由表达式提供的毫秒值每隔指定的时间间隔
     * win:time_length_batch(time period, size [, flow control])	多策略的时间和长度的批处理窗口,当时间或数量任意一个满足条件时，触发查询
     * win:time_accum(time period)	                                阻塞输出，直到 period 内没有新进入的数据时，才输出并触发查询. 这些数据视为已移出窗口
     * win:keepall()	                                            keep-all 无参数，记录所有进入的数据，除非使用delete操作，才能从窗口移出数据
     * ext:sort(size, sort criteria)	                            排序的排序标准表达式返回的值,仅保留事件到给定的大小。 Q:select sum(amount) from userbuy.ext:sort(3, amount desc) 将3个最高金额求和
     * ext:rank(unique criteria(s), size, sort criteria(s))	        只保留最近的事件中有相同的值为标准表达式(s)按某种标准表达式和仅保留顶部事件到给定的大小。 Q:select * from userbuy.ext:sort(3,amount desc,id asc) 找出最高金额的3条事件，并按id排序
     * ext:time_order(timestamp expression, time period)	        Orders events that arrive out-of-order, using an expression providing timestamps to be ordered.
     * std:unique(unique criteria(s))	        对不同的unique[例如:id]保留其最近的一条事件
     * std:groupwin(grouping criteria(s))	    一般与其它窗口组合使用，将进入的数据按表达式 id 分组
     *                                          关于分组的时间窗口:当使用grouped-window时间窗口,注意,是否保留5分钟的事件或每组保留5分钟的事件,
     *                                          结果都是一样的从保留的角度事件既保留政策,考虑所有组,同一组的事件。因此请单独指定时间窗
     * std:lastevent()	                        保留最后一个事件，长度为1
     * std:firstevent()	                        保留第一个事件，不顾后续的事件
     * std:firstunique(unique criteria(s))	    Retains only the very first among events having the same value for the criteria expression(s), disregarding all subsequent events for same value(s).
     * win:firstlength(size)	                保留一批数据的前 size 条，需配合delete操作
     * win:firsttime(time period)	            保留窗口初始化后 period 内的所有数据
     */
    protected static String dataWindowViews() {
        //每进入3个事件后统计输出一次newEvents[3]
        String epl1 = "select price from " + Apple.CLASSNAME + ".win:length_batch(3)";

        //每进入3个事件后，按price分组统计3个事件中每组 price 出现的次数
        // TODO 默认记录了以前的分组统计信息，当前10个事件中不存在以前分组统计信息，则 count(price)为0
        String epl2 = "select rstream price , count(price) from " + Apple.CLASSNAME + ".win:length_batch(3) group by price";

        //[istream | rstream]的规则，在我们插入一批数据后，istream在3秒后输出进入窗口的数据，3秒过后，rstream会输出同样的内容
        String epl3 = "select rstream price , count(price) from " + Apple.CLASSNAME + ".win:time(5 seconds) group by price";

        //TODO unfinished  timestamp ?
        String epl4 = "select price from " + Apple.CLASSNAME + ".win:ext_timed(timestamp, 10 seconds)";

        //win:time_batch(10 sec,"FORCE_UPDATE, START_EAGER") 加上这两个参数后，会在窗口初始化时就执行查询，并且在没有数据进入和移出时，强制查询出结果
        String epl5 = "select * from " + Apple.CLASSNAME + ".win:time_batch(10 sec, \"FORCE_UPDATE, START_EAGER\")";

        // 时间和数量满足任意一个则输出
        String epl6 = "select price from " + Apple.CLASSNAME + ".win:time_length_batch(3 sec, 5, \"FORCE_UPDATE, START_EAGER\")";

        //3s 内没有数据进入则输出结果，并移除数据
        String epl7 = " select rstream price from " + Apple.CLASSNAME + ".win:time_accum(3 sec)";

        //只输出前10个事件
        String epl8 = "select price from " + Apple.CLASSNAME + ".win:firstlength(10)";

        //对 id 和 price 保留最后一条事件记录
        String epl9 = "select price from " + Apple.CLASSNAME + ".std:unique(id, price)";

        return epl8;
    }

    /**
     * TODO unfinished
     * win:expr(expiry expression)	  事件基于过期表达式的结果作为一个参数传递。
     * <p>
     * Table 12.3. Built-in Properties of the Expiry Expression Data Window View
     * <p>
     * Name	            Type	                        Description
     * ================================================================================
     * current_count	int	            在数据窗口包括当前到达的事件的事件的数量
     * expired_count	int	            评估过期的事件数量
     * newest_event	    (same event type as arriving events)	最后一个到达的事件
     * newest_timestamp	long	        引擎的时间戳与last-arriving事件有关。
     * oldest_event	    (same event type as arriving events)	currently-evaluated事件本身。
     * oldest_timestamp	long            引擎的时间戳与currently-evaluated事件有关。
     * view_reference	Object	        当前视图处理的对象
     *
     * @return epl
     */
    protected static String expr() {
        String epl1 = "select price from " + Apple.CLASSNAME + ".win:expr(current_count = 1)";

        String epl2 = "select price from " + Apple.CLASSNAME + ".win:expr(oldest_timestamp > newest_timestamp - 2000)";

        String epl3 = "select price from " + Apple.CLASSNAME + ".win:expr(newest_event.id != oldest_event.id)";

        return epl3;
    }

    /**
     * TODO unfinished
     * win:expr_batch(expiry expression)	    Tumbling window that batches events and releases them based on the result of an expiry expression passed as a parameter.
     * <p>
     * Table 12.4. Built-in Properties of the Expiry Expression Data Window View
     * <p>
     * Name	            Type	                        Description
     * ================================================================================
     * current_count	int	            在数据窗口包括当前到达的事件的事件的数量
     * newest_event	    (same event type as arriving events)	最后一个到达的事件
     * newest_timestamp	long	        引擎的时间戳与last-arriving事件有关。
     * oldest_event	    (same event type as arriving events)	currently-evaluated事件本身。
     * oldest_timestamp	long            引擎的时间戳与currently-evaluated事件有关。
     * view_reference	Object	        当前视图处理的对象
     *
     * @return epl
     */
    protected static String expr_batch() {
        return "";
    }


    /**
     * View	                        Syntax	                                            Description
     * =============================================================================================================================
     * Size	                    std:size([expression, ...])	                                            Derives a count of the number of events in a data window, or in an insert stream if used without a data window, and optionally provides additional event properties as listed in parameters.
     * Univariate statistics    stat:uni(value expression [,expression, ...])	                        Calculates univariate statistics on the values returned by the expression.
     * Regression	            stat:linest(value expression, value expression [,expression, ...])	    Calculates regression on the values returned by two expressions.
     * Correlation	            stat:correl(value expression, value expression [,expression, ...])	    Calculates the correlation value on the values returned by two expressions.
     * Weighted average	        stat:weighted_avg(value expression, value expression [,expression, ...])Calculates weighted average given a weight expression and an expression to compute the average for.
     */
    protected static void derived_Value_Views() {

    }
}
