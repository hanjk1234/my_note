package com.designmodel.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by lw on 14-5-1.
 * <p/>
 * cglib动态代理
 * 项目中计时方法执行计时使用
 */
public class Proxy_Cglib implements MethodInterceptor {

    private Object object;

    /**
     * 创建代理对象
     *
     * @param object
     * @return
     */
    public Object getInstance(Object object) {
        this.object = object;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.object.getClass());
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
