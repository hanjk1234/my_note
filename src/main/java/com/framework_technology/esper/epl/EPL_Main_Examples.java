package com.framework_technology.esper.epl;


import com.espertech.esper.client.*;

/**
 * main 函数测试
 * <p>
 * 1.EPL Syntax
 * <p>
 * [annotations]
 * [expression_declarations]
 * [context context_name]
 * [insert into insert_into_def]
 * select select_list
 * from stream_def [as name] [, stream_def [as name]] [,...]
 * [where search_conditions]
 * [group by grouping_expression_list]
 * [having grouping_search_conditions]
 * [output output_specification]
 * [order by order_by_expression_list]
 * [limit num_rows]
 * <p>
 * 2.Time Periods
 * <p>
 * time-period :  [year-part] [month-part] [week-part] [day-part] [hour-part]
 * [minute-part]  [seconds-part] [milliseconds-part]
 * year-part :    (number|variable_name) ("years" | "year")
 * month-part :   (number|variable_name) ("months" | "month")
 * week-part :    (number|variable_name) ("weeks" | "week")
 * day-part :     (number|variable_name) ("days" | "day")
 * hour-part :    (number|variable_name) ("hours" | "hour")
 * minute-part :  (number|variable_name) ("minutes" | "minute" | "min")
 * seconds-part : (number|variable_name) ("seconds" | "second" | "sec")
 * milliseconds-part : (number|variable_name) ("milliseconds" | "millisecond" |
 * "msec")
 * <p>
 * 3.注解
 * a) @Name 指定EPL的名称，参数只有一个。例如：@Name("MyEPL")
 * b) @Description 对EPL进行描述，参数只有一个。例如：@Description("This is MyEPL")
 * c) @Tag 对EPL进行额外的说明，参数有两个，分别为Tag的名称和Tag的值，用逗号分隔。例如：@Tag(name="author",value="luonanqin")
 * d) @Priority 指定EPL的优先级，参数只有一个，并且整数（可负可正）。例如：@Priority(10)
 * e) @Drop 指定事件经过此EPL后不再参与其他的EPL计算，该注解无参数。
 * f) @Hint 为EPL加上某些标记，让引擎对此EPL产生其他的操作，会改变EPL实例的内存占用，但通常不会改变输出。其参数固定，由Esper提供，之后的篇幅会穿插讲解这个注解的使用场景。
 * g) @Audit EPL添加此注解后，可以额外输出EPL运行情况，有点类似日志的感觉（当然没有日志的功能全啦），具体使用场景在此先不提。
 * h) @Hook 与SQL相关，这里暂且不说
 * i) @EventRepresentation 这是用来指定EPL产生的计算结果事件包含的数据形式。参数只有一个，即array=true或array=false。false为默认值，代表数据形式为Map，若为true，则数据形式为数组。
 *
 * @author wei.Li
 */
public class EPL_Main_Examples implements Runnable {

    //线程执行时间间隔-ms
    private static final int EXECUTE_INTERVAL_MILLISECOND = 1000;
    //执行次数
    private static final int EXECUTE_NUM = 10;

    private static final EPServiceProvider defaultProvider = EPServiceProviderManager.getDefaultProvider();
    private static final EPAdministrator epAdministrator = defaultProvider.getEPAdministrator();
    private static final EPRuntime epRuntime = defaultProvider.getEPRuntime();


    /**
     * @return 价格的平均数 epl
     */
    private static String avg() {
        return "select " + Apple.AVG_PRICE + " from " + Apple.CLASSNAME + ".win:length_batch(3)";
    }

    /**
     * @return 时间的周期性 epl
     */
    private static String timePeriods() {

        // 计算过去的5分3秒中进入改语句的Apple事件的平均price
        return "select avg(price) from  " + Apple.CLASSNAME + ".win:time(0 minute 3 sec)";

        // 每一天输出一次用户的账户总额
        //return "select sum(price) from " + Apple.CLASSNAME + " output every 1 day";
    }

    /**
     * EPL支持Java所有的数值数据类型，包括基本类型及其包装类，同时还支持java.math.BigInteger和java.math.BigDecimal，
     * 并且能自动转换数据类型不丢失精度（比如short转int，int转short则不行）。
     * 如果想在EPL内进行数据转换，可以使用cast函数。
     * TODO key = cast(avg(price), int)
     *
     * @return epl
     */
    private static String cast() {

        return "select cast(avg(price),int) from " + Apple.CLASSNAME + ".win:length_batch(2)";
    }

    /**
     * 注解支持
     *
     * @return epl
     */
    private static String annotation() {
        /**
         @Hint

         reclaim_group_aged
         该属性后面跟着的是正整数，以秒为单位，表示在n秒内，若分组的数据没有进行更新，则分组数据被Esper回收。
         // 根据color对10秒内进入的Apple事件进行分组计算平均price,并且对5秒内没有数据更新的分组进行回收
         @Hint('reclaim_group_aged=5')select avg(price) as aPrice, color from Apple.win:time(10 sec) group by color //括号内可以使单引号也可以是双引号

         reclaim_group_freq
         该属性后面跟着的是正整数，以秒为单位，表示每n秒清理一次分组，可清理的分组是reclaim_group_aged决定的，也就是说要使用该参数，就要配合reclaim_group_aged一起使用。
         // 根据color对10秒内进入的Apple事件进行分组计算平均price。对8秒内没有数据更新的分组进行回收,每2秒回收一次
         @Hint('reclaim_group_aged=8，reclaim_group_freq=2')select avg(price) as aPrice, color from Apple.win:time(10 sec) group by color

         */
        String epl1 = "@Priority(10)@EventRepresentation(array=true) select sum(price) from " + Apple.CLASSNAME + ".win:length_batch(2)";
        String epl2 = "@Name(\"EPL2\")select sum(price) from " + Apple.CLASSNAME + ".win:length_batch(2)";
        String epl3 = "@Drop select sum(price) from " + Apple.CLASSNAME + ".win:length_batch(2)";
        // TODO @Name和@EventRepresentation都起效果了，但是@Priority和@Drop没用，那是因为这两个是要配置才能生效的。以后Esper的Configuration涉及
        return epl1;
    }

    /**
     * Expression类似自定义函数，通常用Lambda表达式来建立的（也有别的方法建立），而Lambda表达式就一个“ =&gt; ”符号，表示“gose to”。
     * <p>
     * 符号的左边表示输入参数，符号右边表示计算过程，计算结果就是这个表达式的返回值，即Expression的返回值。
     * <p>
     * 语法如下:
     * <p>
     * expression是关键字，expression_name为expression的名称（唯一），expression_body是expression的具体内容。
     * <p>
     * {@code expression expression_name { expression_body }}
     *
     * @return epl
     */
    private static String expression() {
        /**

         针对变量和常量的定义
         expression twoPI { Math.PI * 2} select twoPI() from SampleEvent

         先定义全局的avgPrice
         create expression avgPrice { x => (x.fist+x.last)/2 }

         bananaPrice Banana事件中包含了first和last属性，否则将报错
         expression bananaPrice{ x => avgPrice(x) } select bananaPrice(b) from Banana as b
         *
         */

        return "expression sumprice{ (x,y) => x.price + y.price } select sumprice(me,you) from " + Apple.CLASSNAME + " as me, " + Apple.CLASSNAME + " as you";
    }

    /**
     * EPL的select和SQL的select很相近，SQL用*表示查询表的所有字段，而EPL用*表示查询事件流的所有属性值。
     * SQL查询某个字段名，直接在select后跟字段名就ok，EPL也是将要查询的属性名放在select之后。
     * 若查多个属性值，则用逗号分割。和SQL一样，EPL查询属性也可以设置别名
     * <p>
     * 如果查询的是一个完整对象，需要调用getUnderlying()方法，而get方法是针对确定的属性名或者别名。另外*是不能设置别名的。
     *
     * @return epl
     */
    private static String selectClause() {
        // EPL：查询完整的Apple对象
        String epl1 = "select * from " + Apple.CLASSNAME;
        /**
         获取User对象
         Apple u = newEvent.getUnderlying();

         EPL：查询User的name和id，id别名为i
         select id as i, price as i from Apple
         获取name和id
         String price = (String) newEvent.get("p");
         int id = (Integer) newEvent.get("i");
         */

        /**
         调用方法
         @see Apple#getPriceByDiscount()
         @see Apple#getPriceByDiscount(int, double)
         事件流需要设置别名才能使用其方法，如：r.getPriceByDiscount()
         */
        //计算该Apple 折扣后的价格
        String epl2 = "select price * discount as discount_price from " + Apple.CLASSNAME;

        // 计算该Apple 折扣后的价格
        String epl3 = "select r.getPriceByDiscount(r.price,r.discount) as discount_price from " + Apple.CLASSNAME + " as r";

        String epl4 = "select r.getPriceByDiscount() as discount_price from " + Apple.CLASSNAME + " as r";

        /**
         静态方法
         如果Apple类提供了一个专门计算的静态方法，表达式也可以直接引用。
         不过要事先加载这个包含方法的类

         注意一定要是静态方法，不然没有实例化是没法引用的。
         */
        epAdministrator.getConfiguration().addImport(Apple.class);
        String epl5 = "select " + Apple.CLASSNAME + ".getPriceByDiscount2StaticMethod(r.price,r.discount) as discount_price from " + Apple.CLASSNAME + " as r";

        /**
         多事件流的查询

         最好还是带上别名，万一哪天另一个事件流多了一个一样的属性

         当老师的id和学生的id相同时，查询学生的姓名和老师的姓名
         select s.name, t.name from Student.win:time(10) as s, Teacher.win:time(10) as t where s.id=t.id
         如果想查询Student或者Teacher，则EPL改写如下：
         select s.* as st, t.* as tr from Student.win:time(10) as s, Teacher.win:time(10) as t where s.id=t.id
         */

        /**
         insert和remove事件流
         TODO unfinished
         Esper对于事件流分输入和移出两种，分别对应监听器的两个参数newEvents和oldEvents，
         关于监听器的内容可参看-Esper学习之三：进程模型<a>http://blog.csdn.net/luonanqin/article/details/10714687</a>。
         newEvents通常对应事件的计算结果，oldEvents可以理解过上一次计算结果。
         默认情况下，只有newEvents有值，oldEvents为null。
         如果需要查看oldEvents，则需要使用一个参数。
         */
        String epl6 = "select rstream * from " + Apple.CLASSNAME;
        String epl7 = "select irstream * from " + Apple.CLASSNAME;
        /**
         select istream * from User
         等同于
         select * from User
         */

        /**
         Distinct
         distinct的用法和SQL一样，放在需要修饰的属性或者*前即可。
         TODO 3s内Distinct对象，应该用equals 进行比较？ 测试没有结果！！！
         */
        String epl8 = "select distinct * from " + Apple.CLASSNAME + ".win:time(3 sec)";

        // 引擎URI为Processor
        /**
         查询指定引擎的处理结果

         上述一些特点外，select还可以针对某个引擎进行查询。
         因为引擎都有自己的URI，所以可以在select句子中增加URI标识来指定查询哪一个引擎的事件处理情况
         */
        String epl9 = "select Processor.MyEvent.myProperty from Processor.MyEvent";
        return epl9;
    }

    /**
     * 事件流的过滤
     * <p>
     * 要过滤的属性只能是数字和字符串。
     * 过滤表达式中不能使用聚合函数。
     * “prev”和“prior”函数不能用于过滤表达式（暂且不考虑这是什么）
     *
     * @return epl
     */
    protected static String fromClause() {

        /**
         事件属性过滤

         事件流过滤通常情况都是对其中某个或多个属性加以限制来达到过滤的目的。注意，过滤表达式是紧跟在事件流名称之后而不是别名之后。
         */
        // 查询年龄大于15小于18的学生的姓名
        String epl1 = " select name from Student(age between 15 and 18)";
        // 等同于
        String epl2 = "select name from Student (age >= 15 and age<=18)";
        // 等同于
        String epl3 = "select name from Student (age >= 15, age <= 18)";
        /**
         过滤范围

         刚才说到过滤表达式使用的符号很多，总结下来基本上有<, >, <=, >=, =, !=, between, in, not in, and, or, [ ], ( )

         这里主要说下between，in，( )，[ ]
         between……and……
         和SQL的between……and……意思一样，是一个闭区间。比如说between 10 and 15，中文语义为10到15之间并包含10和15.

         ( )
         表示一个开区间，语法为(low：high)。如(10:15)，表示10到15之间，并且不包含10和15

         [ ]
         表示一个闭区间，语法为[low：high]。如[10:15]，表示10到15之间，并且包含10和15
         ( )和[ ]可以混合用。比如[10:15)或者(10:15]

         in
         配合( )和[ ]进行使用，表示值在某个范围内。

         select name from User(age in [10:15))
         select age from User(name in ('张三', '李四'))
         */

        /**
         静态方法过滤

         返回值必须为布尔值，不然会报错。
         查询没有钱的用户的name值（User包含name和money属性）
         select name from User(IsZero.isZero(money))
         */
        return "";
    }

    /**
     * 聚合函数
     * <p>
     * aggregate_function([all|distinct] expression)
     * aggregate_function就是聚合函数的名字，比如avg，sum等。expression通常是事件流的某个属性，也可以是不同事件流的多个属性，或者是属性和常量、函数之间的运算
     * <p>
     * 1.聚合函数能用于Select和Having，但是不能用于Where
     * 2.sum,avg,media,stddev,avedev只能计算数值，至于media，stddev和avedev代表什么意思，请自行百度。
     * 3.Esper会忽略expression为null不让参与聚合运算，但是count函数除外，即使是null也认为是一个事件。
     * 如果事件流集合中没有包含任何事件，或者包含的事件中用于聚合计算的expression都是null（比如收集5秒内进入的事件即为一个事件流集合），则所有聚合函数都返回null。
     *
     * @return epl
     */
    protected static String aggregation() {
        /**
         查询最新5秒的Apple的平均价格
         select avg(price) as aPrice from Apple.win:time(5 sec)

         查询最新10个Apple的价格总和的两倍
         select sum(price*2) as sPrice from Apple.win:length(10)

         查询最新10个Apple的价格，并用函数计算后再算平均值
         select avg(Compute.getResult(price)) from Apple.win:length(10)
         函数只能是静态方法，普通方法不可用。即使是事件流里包含的静态方法，也必须用“类名.方法名”的方式进行引用。

         可以使用distinct关键字对expression加以约束，表示去掉expression产生的重复的值。默认情况下为all关键字，即所有的expression值都参与聚合运算。
         查询最新5秒的Apple的平均价格
         select avg(distinct price) as aPrice from Apple.win:time(5 sec)

         假如：5秒内进入了三个Apple事件，price分别为2,1,2。则针对该EPL的平均值为(2+1)/2=1.5。因为有distinct的修饰，所以第二个2不参与运算，事件总数即为2，而不是3。
         */
        return "";
    }

    /**
     * Group by
     * <p>
     * 通常配合聚合函数使用。语法和SQL基本一样，产生的效果就是以某一个或者多个字段进行分组，然后使聚合函数作用于不同组的数据
     * group by aggregate_free_expression [, aggregate_free_expression] [, ...]
     * <p>
     * 使用Group by要注意一下几点：
     * 1.Group by后面的内容不能包含聚合函数
     * 2.Group by后面的内容不能是之前select子句中聚合函数修饰的属性名
     * 3.通常情况要保证分组数量有限制，以防止内存溢出。但是如果分组分了很多，就需要使用@Hint加以控制。
     * @see EPL_Main_Examples#annotation()
     *
     *
     * @return epl
     */
    protected static String groupBy() {
        //TODO Conslole : Apple'saPrice is <null>,color  is <2> ,size is <1>"   NULL?记忆了上次计算的数据
        // 根据color和size来对10个Apple事件进行分组计算平均price
        String epl1 = "select avg(price) as aPrice, color, size from " + Apple.CLASSNAME + ".win:length_batch(5) group by color,size";
        return epl1;
    }

    public static void main(String[] args) throws InterruptedException {

        //获取 epl
        String epl = groupBy();

        EPStatement epStatement = epAdministrator.createEPL(epl);
        epStatement.addListener(new AppleListener());

        new EPL_Main_Examples().run();

    }

    @Override
    public void run() {

        int temp = 1;
        while (temp <= EXECUTE_NUM) {
            temp++;
            epRuntime.sendEvent(Apple.getRandomApple());
            try {
                Thread.sleep(EXECUTE_INTERVAL_MILLISECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}




