package com.designmodel.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by lw on 14-5-1.
 */
public class BookFacadeProxy_JDK implements InvocationHandler {

    private Object object;

    /**
     * 绑定委托对象并返回一个代理类
     *
     * @param object 委托对象
     * @return 代理类
     */
    public Object bind(Object object) {
        this.object = object;
        //取得代理对象
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), this);   //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before ...");
        method.invoke(object, args);
        System.out.println("after ...");
        return null;
    }
}


