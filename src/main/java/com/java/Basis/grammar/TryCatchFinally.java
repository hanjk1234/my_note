package com.java.basis.grammar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-5
 * Time: 9:38
 * <p>
 * ①： finally块的语句在try或catch中的return语句执行之后返回之前执行且finally里的修改语句可能影响也可能不影响try或catch中 return已经确定的返回值，
 * 因为如果finally里也有return语句则覆盖try或catch中的return语句直接返回。
 * <p>
 * ②：finally块的语句在try或catch外的return语句执行之后返回之前执行且finally里的修改语句影响try或catch外 return的返回值
 * <p>
 * 值传递、地址传递
 */
public class TryCatchFinally {


    /**
     * 基本数据类型
     * try{}中有异常测试
     *
     * @return int
     */
    private static int hasException() {
        int temp = 0;
        try {
            temp = temp / 0;
            System.out.println("not have Exception ,return 0");
            return temp--;
        } catch (Exception e) {
            temp += 100;
            System.out.println("run  catch{ i += 100; }  i=" + temp);
            return temp;
        } finally {
            temp++;
            System.out.println("run  finally{   i++;  }  i=" + temp);
            //return 2;
        }
        //return temp;
    }
    /*
    执行结果:
    run  catch{ i += 100; }  i=100
    run  finally{   i++;  }  i=101
    return : 100
     */


    /**
     * 基本数据类型
     * try{}中无异常测试
     *
     * @return int
     */
    private static int notHasException() {
        int temp = 0;
        try {
            return temp--;
        } catch (Exception e) {
            temp += 100;
            System.out.println("run  catch{ i += 100; }  i=" + temp);
            return temp--;
        } finally {
            temp++;
            System.out.println("run  finally{   i++;  }  i=" + temp);
            //return 2;
        }
        //return temp;
    }
    /*
    执行结果:
    run  finally{   i++;  }  i=0
    return : 0
     */


    /**
     * 引用类型
     * try{}中有异常测试
     *
     * @return Map
     */
    public static Map<String, String> stringMapHasException() {
        Map<String, String> map = new HashMap<>();
        map.put("Key", "init");
        try {
            map.put("Key", "try");
            int i = 1 / 0;
            return map;
        } catch (Exception e) {
            map.put("Key", "catch");
            System.out.println("run catch{}  map=" + map);
            return map;
        } finally {
            //map.put("Key", "finally");
            map = null;
            System.out.println("run finally{ map.put(\"Key\", \"finally\"); map = null;} ,  map=" + map);
        }
        //return map;
    }
    /*
    执行结果:
    run catch{}  map={Key=catch}
    run finally{ map.put("Key", "finally"); map = null;} ,  map=null
    return : {Key=catch}
     */


    /**
     * 引用类型
     * try{}中无异常测试
     *
     * @return Map
     */
    public static Map<String, String> stringMapNotHasException() {
        Map<String, String> map = new HashMap<>();
        map.put("Key", "init");
        try {
            map.put("Key", "try");
            return map;
        } catch (Exception e) {
            map.put("Key", "catch");
            System.out.println("run catch{}  map=" + map);
            return map;
        } finally {
            map.put("Key", "finally");
            map = null;
            System.out.println("run finally{ map.put(\"Key\", \"finally\"); map = null;} ,  map=" + map);
            return map;
        }
        //return map;
    }
    /*
    执行结果:
    run finally{ map.put("Key", "finally"); map = null;} ,  map=null
    return : null
     */


    public static void main(String[] args) {
        System.out.println("return : " + stringMapNotHasException());
    }

}
