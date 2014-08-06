package com.java.Basis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-5
 * Time: 9:38
 * <p>
 * finally块的语句在try或catch中的return语句(不含try,catch外)执行之后返回之前执行且finally里的修改语句可能影响也可能不影响try或catch中 return已经确定的返回值，
 * 因为若finally里也有return语句则覆盖try或catch中的return语句直接返回。
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
            temp = temp / 1;
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

    /**
     * 基本数据类型
     * try{}中无异常测试
     *
     * @return int
     */
    private static int notHasException() {
        int temp = 0;
        try {
            temp = temp / 1;
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
    }


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

    }

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
    }


    public static void main(String[] args) {
        System.out.println("return : " + hasException());

    }

}
