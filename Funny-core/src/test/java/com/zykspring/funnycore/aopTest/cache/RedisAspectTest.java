package com.zykspring.funnycore.aopTest.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisAspectTest {

    @Pointcut("execution(* com.zykspring.funnycore.aopTest.db.DataBaseTest.getOne(long)) && args(arg1)")
    public void pointCut1(long arg1){}

    @Pointcut("execution(* com.zykspring.funnycore.aopTest.db.DataBaseTest.getAll(..))")
    public void pointCut2(){}

    @Before("pointCut1(arg1)")
    public void before(long arg1){
        System.out.println("before method arg1:"+arg1);
//        return "before";
    }

    @After("pointCut1(arg1)")
    public void after(JoinPoint joinPoint,long arg1){
        Object[] args = joinPoint.getArgs();
        for(Object o:args){
            System.out.println("after args : "+String.valueOf(o));
        }
    }

    @Around("pointCut1(arg1)")
    public Object run1(ProceedingJoinPoint joinPoint,long arg1){

        String appId = "";
        String result = null;

        Object[] args = joinPoint.getArgs();
        if(args != null && args.length > 0){
            appId = String.valueOf(args[0]);
        }

        System.out.println("redis find id: "+appId+" arg1: ");

//        result = "redis: "+appId;

        if(null != result){
            System.out.println("redis get data id: "+appId);
            return result;
        }

        System.out.println("redis don't find data id: "+appId);

        try {
            result = String.valueOf(joinPoint.proceed());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.out.println("around is end !");

        return result;
    }

}
