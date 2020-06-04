package com.zykspring.funnycore.cglibTest;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TestInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("in interceptor and method is "+method.getName());
        long startTime = getTime();

        methodProxy.invokeSuper(o,objects);

        System.out.println("use time is "+ (getTime()-startTime));
        return null;
    }

    private long getTime(){
        return System.currentTimeMillis();
    }
}
