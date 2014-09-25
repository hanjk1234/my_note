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

package com.thread.basis;

import java.util.function.Supplier;

/**
 * @author wei.Li by 14-9-10.
 */
public class ThreadLocal_ {

    public static void main(String[] args) {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("");
        threadLocal.get();
        threadLocal.remove();

        ThreadLocal.withInitial(new Supplier<Object>() {
            @Override
            public Object get() {
                return null;
            }
        });
    }


}
