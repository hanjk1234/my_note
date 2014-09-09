package com.designmodel.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lw by 14-5-1.
 */
public class BookFacadeProxy_Cglib implements MethodInterceptor {

    /**
     * 创建代理对象
     *
     * @param object object
     * @return 创建代理对象
     */
    public Object getInstance(Object object) {
        Object object1 = object;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object1.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    // 回调方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        long start = System.currentTimeMillis();
        proxy.invokeSuper(obj, args);
        System.out.println(proxy.getSuperName() + "执行耗时：" + (System.currentTimeMillis() - start) + "ms");
        return null;
    }
}
