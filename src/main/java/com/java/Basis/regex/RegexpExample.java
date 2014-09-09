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
    public static final String s = "" +
            "(?:[^[:punct:]\\s[:cntrl:]'‘’]+[’'][^[:punct:]\\s[:cntrl:]'‘’]+)" +
            " | " +
            "[^[:punct:]\\s[:cntrl:]'‘’]";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RegexpExample.class);

    private static void runExample(String regexp, String s) {
        Pattern pattern = Pattern.compile(regexp);
        final Matcher matcher = pattern.matcher(s);
        boolean b = matcher.matches();
        LOGGER.info("[ {} ] matcher [ {} ] , result [ {} ]", pattern.pattern(), s, b);
    }

    private static void runExampleGroup(String regexp, String s) {
        Pattern pattern = Pattern.compile(regexp);
        final Matcher matcher = pattern.matcher(s);
        String result = null;
        while (matcher.find()) {
            result = matcher.group();
        }
        LOGGER.info("[ {} ] matcher [ {} ] , result [ {} ]", pattern.pattern(), s, result);
    }

    public static void main(String[] args) {

        runExampleGroup(s, " sdfsdfsdf asd");

    }

}
