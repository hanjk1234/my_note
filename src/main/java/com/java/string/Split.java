package com.java.string;

import com.designmodel.proxy.BookFacadeProxy_Cglib;

import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: lw
 * Date: 14-5-5
 */
public class Split {


    private String str = getString();

    private String temp;

    /**
     * 初始化字符串
     *
     * @return
     */
    private String getString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            builder.append(i + ";");
        }
        return builder.toString();
    }

    public void split2str() {

        String[] strs = str.split(";");
        for (int i = strs.length - 1; i >= 0; i--) {
            temp = strs[i];
        }
    }

    public void stringTokenizer2str() {
        StringTokenizer tokenizer = new StringTokenizer(str, ";");
        while (tokenizer.hasMoreElements()) {
            temp = (String) tokenizer.nextElement();
        }
    }

    /**
     * 采用indexOf与substring方法实现
     */
    public void indexOf2str() {
        String strTemp = str;
        int j = 0;
        while (true) {
            j = strTemp.indexOf(";");
            if (j < 0) {
                break;
            }
            temp = strTemp.substring(0, j);
            strTemp = strTemp.substring(j + 1);
        }

    }

    public static void main(String[] args) {

        BookFacadeProxy_Cglib cglib = new BookFacadeProxy_Cglib();
        Split split = (Split) cglib.getInstance(new Split());

        split.split2str();
        split.indexOf2str();
        split.stringTokenizer2str();
    }
}
