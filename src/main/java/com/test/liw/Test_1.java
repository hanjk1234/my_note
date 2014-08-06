package com.test.liw;

import java.io.Serializable;
import java.util.RandomAccess;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-1
 * Time: 10:51
 */
public class Test_1 implements Serializable,Cloneable,RandomAccess {


    public static String string_1 = "string_1";

    public String string_2;

    static {
        string_1 = "string _3";
    }

    {
        System.out.println(string_2);

        string_2 = getString_2();

        System.out.println(string_2);
    }


    Test_1() {
        System.out.println(Test_1.string_1);
        System.out.println(string_2);
    }


    private String getString_1() {
        return "method result String_1";
    }


    private String getString_2() {
        return "method result String_2";
    }


    private static int aVoid() {
        for (int i = -1; i < 1; i++) {
            try {
                System.out.println("for -> i is:" + i);
                int temp = 10 / i;
            } catch (Exception e) {
                i = 1;
                return i;
            } finally {
                i++;
                System.out.println("finally ...  i is" + i);
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        // new Test_1();
        System.out.println(aVoid());
    }

}
