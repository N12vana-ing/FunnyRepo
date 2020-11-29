package com.zykspring.funnycore.processorTest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;

public class RoutingBeanProxyFactory {
    private final static String DEFAULT_BEAN_NAME = "helloServiceImpl1";

    public static Object createProxy(String name, Class type, Map<String, Object> candidates) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(type);
        proxyFactory.addAdvice(new VersionRoutingMethodInterceptor(name, candidates));
        return proxyFactory.getProxy();

    }

    public void addMyAdvice(ProxyFactory proxyFactory){
        //设置目标对象（可以是Object）
//        proxyFactory.setTarget(new Man());

        //添加方法前置通知
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target)
                    throws Throwable {
                System.out.println("在吃饭方法调用之前拦截:吃饭前要洗手");
            }
        });
        //可以添加多个方法前置或者后置通知
        proxyFactory.addAdvice(new MethodBeforeAdvice() {

            @Override
            public void before(Method method, Object[] args, Object target)
                    throws Throwable {
                System.out.println("在吃饭方法调用之前拦截-2：要等长辈先吃");
            }
        });
        //可以添加多个方法前置或者后置通知
        proxyFactory.addAdvice(new AfterReturningAdvice() {

            @Override
            public void afterReturning(Object returnValue, Method method,
                                       Object[] args, Object target) throws Throwable {
                System.out.println("吃饭方法完成之后调用：饭后要洗碗");

            }
        });

        //可以添加多个方法前置或者后置通知
        proxyFactory.addAdvice(new AfterReturningAdvice() {

            @Override
            public void afterReturning(Object returnValue, Method method,
                                       Object[] args, Object target) throws Throwable {
                System.out.println("吃饭方法完成之后调用2：饭后散下步");

            }
        });


        //对于环绕通知只能添加一个,多添加也是没有用的，spring会选第一个advice，请看结果

        proxyFactory.addAdvice(new MethodInterceptor() {

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {

                Object aThis = invocation.getThis();
                System.out.println("方法环绕调用前："+aThis);

                Object proceed = invocation.proceed();//执行被代理对象的方法,返回方法的返回值

                System.out.println("方法环绕调用后："+proceed.toString());

                return proceed;
            }
        });

        //对于环绕通知只能添加一个,多添加也是没有用的，spring会选第一个advice，请看结果
        proxyFactory.addAdvice(new MethodInterceptor() {

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {

                Object aThis = invocation.getThis();
                System.out.println("方法环绕调用前2："+aThis);

                Object proceed = invocation.proceed();//执行被代理对象的方法,返回方法的返回值

                System.out.println("方法环绕调用后2："+proceed.toString());

                return proceed;
            }
        });
    }

    static class VersionRoutingMethodInterceptor implements MethodInterceptor {
        private Object targetObject;

        public VersionRoutingMethodInterceptor(String name, Map<String, Object> beans) {
            this.targetObject = beans.get(name);
            if (this.targetObject == null) {
                this.targetObject = beans.get(DEFAULT_BEAN_NAME);
            }
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("in VersionRoutingMethodInterceptor method before");
            Object object = invocation.getMethod().invoke(this.targetObject, invocation.getArguments());
            System.out.println("in VersionRoutingMethodInterceptor method after");
            return object;
        }
    }

}
