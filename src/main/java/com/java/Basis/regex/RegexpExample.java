/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package com.java.basis.regex;

import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wei.Li by 14-9-4.
 */
public class RegexpExample {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RegexpExample.class);


    private static void runExample(String regexp, String s) {
        Pattern pattern = Pattern.compile(regexp);
        final Matcher matcher = pattern.matcher(s);
        boolean b = matcher.matches();
        LOGGER.info("[ {} ] matcher [ {} ] , result [ {} ]", pattern.pattern(), s, b);
    }

    public static void main(String[] args) {

        // runExample("([\\s]{1}[.]+)", " 1 ");

        int i = 32111;//10 进制数
        String hex16 = Integer.toBinaryString(i); //转为16进制 用字符串表示
        System.out.println(hex16);

    }

}
