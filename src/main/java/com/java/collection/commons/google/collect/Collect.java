package com.java.collection.commons.google.collect;

import com.google.common.base.Function;
import com.google.common.collect.*;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author wei.Li by 14-8-25.
 */
public class Collect {

    /**
     * <dependency>
     * <groupId>com.google.guava</groupId>
     * <artifactId>guava</artifactId>
     * <version>17.0</version>
     * </dependency>
     */
    public static void main(String[] args) {
        //treeRangeSet();

        //Multiset_.multiset();
        //Multimap_.hashMultimap();
        //BigMap_.biMap();

    }
}

/**
 * RangeSet类是用来存储一些不为空的也不相交的范围的数据结构.
 * 假如需要向RangeSet的对象中加入一个新的范围，那么任何相交的部分都会被合并起来，所有的空范围都会被忽略.
 * <p>
 * 实现了RangeSet接口的类有ImmutableRangeSet和TreeRangeSet.
 * ImmutableRangeSet是一个不可修改的RangeSet，而TreeRangeSet是利用树的形式来实现.
 */
class RangeSet_ {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(RangeSet_.class);

    private static void immutableRangeSet() {
        //不可变的区间 set
        RangeSet immutableRangeSet
                = ImmutableRangeSet.of(Range.closed(1, 2));
        LOGGER.info("immutableRangeSet is <{}>", immutableRangeSet);
    }

    private static void treeRangeSet() {

        RangeSet<Integer> treeRangeSet = TreeRangeSet.create();
        treeRangeSet.add(Range.closed(1, 10));       // {[1, 10]}
        treeRangeSet.add(Range.closedOpen(11, 15)); // {[1, 10], [11, 15)}
        treeRangeSet.add(Range.open(15, 20));       // 不连贯的范围; {[1‥10], [11‥15), (15‥20)}
        treeRangeSet.add(Range.openClosed(0, 0));   // 空范围; {[1, 10], [11, 20)}
        treeRangeSet.remove(Range.open(5, 10));     // 拆分范围 [1, 10];

        // 最终结果 {[1‥5], [10‥10], [11‥15), (15‥20)}
        LOGGER.info("create treeRangeSet        is <{}>", treeRangeSet.toString());

        //某值是否存在
        LOGGER.info("treeRangeSet.contains(11)  is <{}>", treeRangeSet.contains(20));//false

        //某值所在的范围，不存在返回 null
        LOGGER.info("treeRangeSet.rangeContaining(1)  is <{}>",
                treeRangeSet.rangeContaining(1));//[1‥5]

        //遍历
        for (Range<Integer> integerRange : treeRangeSet.asRanges()) {

            LOGGER.info("integerRange  is <{}>", integerRange);//[1‥5] ...for...
            integerRange.lowerEndpoint();//1
            integerRange.upperEndpoint();//5
        }

        RangeSet complement = treeRangeSet.complement();
        //[(-∞‥1), (5‥10), (10‥11), [15‥15], [20‥+∞)]
        LOGGER.info("treeRangeSet.complement() is <{}>", complement);

    }
}

/**
 * Multiset:把重复的元素放入集合
 * [car x 2, ship x 6, bike x 3]
 * <p>
 * 常用实现 Multiset 接口的类有：
 * HashMultiset: 元素存放于 HashMap
 * LinkedHashMultiset: 元素存放于 LinkedHashMap，即元素的排列顺序由第一次放入的顺序决定
 * TreeMultiset:元素被排序存放于TreeMap
 * EnumMultiset: 元素必须是 enum 类型
 * ImmutableMultiset: 不可修改的 Mutiset
 */
class Multiset_ {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(Multiset_.class);

    protected static void multiset() {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a", 10);
        multiset.add("b", 2);

        //[a x 10, b x 2]
        LOGGER.info("create multiset is <{}>", multiset);

        for (Multiset.Entry<String> s : multiset.entrySet()) {
            s.getElement();
            s.getCount();
        }
        //2
        LOGGER.info("multiset.count(\"b\") is <{}>", multiset.count("b"));

    }

}

/**
 * Multimap: 在 Map 的 value 里面放多个元素
 * <p>
 * Muitimap 就是一个 key 对应多个 value 的数据结构。
 * {k1=[v1, v2, v3], k2=[v7, v8],....}
 * <p>
 * Muitimap 接口的主要实现类有：
 * HashMultimap: key 放在 HashMap，而 value 放在 HashSet，即一个 key 对应的 value 不可重复
 * ArrayListMultimap: key 放在 HashMap，而 value 放在 ArrayList，即一个 key 对应的 value 有顺序可重复
 * LinkedHashMultimap: key 放在 LinkedHashMap，而 value 放在 LinkedHashSet，即一个 key 对应的 value 有顺序不可重复
 * TreeMultimap: key 放在 TreeMap，而 value 放在 TreeSet，即一个 key 对应的 value 有排列顺序
 * ImmutableMultimap: 不可修改的 Multimap
 */
class Multimap_ {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(Multimap_.class);

    protected static void hashMultimap() {
        HashMultimap<String, String> hashMultimap
                = HashMultimap.create();
        hashMultimap.put("a", "1");
        hashMultimap.put("a", "2");
        hashMultimap.put("b", "1");
        hashMultimap.put("b", "2");

        //{a=[1, 2], b=[1, 2]}
        LOGGER.info("create hashMultimap is <{}>", hashMultimap);

        //[1, 2]
        Set<String> stringSet = hashMultimap.get("a");
        LOGGER.info("hashMultimap.get(\"a\") is <{}>", stringSet);

    }

}


/**
 * BiMap: 双向 Map
 * <p>
 * BiMap 实现了 java.util.Map 接口。
 * 它的特点是它的 value 和它 key 一样也是不可重复的，换句话说它的 key 和 value 是等价的。
 * 如果你往 BiMap 的 value 里面放了重复的元素，就会得到 IllegalArgumentException。
 */
class BigMap_ {
    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(BigMap_.class);

    protected static void biMap() {
        BiMap<String, String> biMap = HashBiMap.create();

        biMap.put("a", "1");

        /**
         * 在BiMap中，如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常。
         * 如果对特定值，你想要强制替换它的键,使用 forcePut();
         */
        String forcePut = biMap.forcePut("a", "2");
        LOGGER.info("forcePut(\"a\", \"2\") is <{}>", forcePut);//1

        String put = biMap.put("b", "1");
        LOGGER.info("put(\"b\", \"1\") is <{}>", put);//null

        //{b=1, a=2}
        LOGGER.info("last the biMap is <{}>", biMap);

        //反转后，键值互换 {1=b, 2=a}
        LOGGER.info("biMap.inverse is <{}>", biMap.inverse());
    }
}

class MapMaker_ {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(MapMaker_.class);

    static void aVoid() {

    }
}