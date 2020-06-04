package com.zykspring.funnycore.aopTest.db;

/**
 * 此类用来测试spring aop使用
 * cglib还是jdk的proxy
 *
 * 测试结果为：
 * 当代理类未实现接口使用cglib——通过getclass打印名称可观察
 * 当代理类实现某接口则通过jdk-proxy
 */
public interface DataBaseMgr {
    String getOne(long id) throws Exception;
}
