package com.zykspring.funnycore.cache;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.zykspring.funnycore.base.BaseDTO;
import com.zykspring.funnycore.plugins.MyRedisAnno;
import com.zykspring.funnycore.util.Reflections;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis切面类
 * 在操作数据成功后
 * 添加redis数据
 *
 * 查询数据库时
 * 添加查询redis操作
 *
 */

@Aspect
@Component
public class RedisAspect<T> {

    private static final Logger log = LoggerFactory.getLogger(RedisAspect.class);

    private Class<T> entityClass;

    @Autowired
    RedisOperator redisOperator;

    @Pointcut("execution(* com.zykspring.funnycore.base.BaseService.get(..))")
    public void selectOneCut(){}

    @Pointcut("execution(* com.zykspring.funnycore.base.BaseService.insert*(..))")
    public void insertCut1(){}

    @Around("@annotation(com.zykspring.funnycore.plugins.MyRedisAnno)")
//    @Around("selectOneCut()")
    public BaseDTO selectRedis(ProceedingJoinPoint joinPoint){
        String redisKey = "";
        BaseDTO result = null;

        //获取调用方法参数
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0)
            return null;

        //通过反射获取当前查询对象类型
        Class target = joinPoint.getTarget().getClass();
        this.entityClass = Reflections.getClassGenericType(target);
        String typeName = entityClass.getName();
        System.out.println(typeName);

        if(args.length==1){
            Object arg = args[0];
            if(arg instanceof Long){
                redisKey = typeName+"-"+arg;
            }
        }

        ValueOperations<String, Object> valueOperator = redisOperator.getValueOperator();

        try {
            if(redisOperator.existsKey(redisKey)){
                log.info("Redis has key: "+redisKey);
                result = (BaseDTO) valueOperator.get(redisKey);
                return result;
            }

            result = (BaseDTO)joinPoint.proceed();
            log.info("select in database key: "+redisKey+ " || "+result);
            valueOperator.set(redisKey,result);

        } catch (Throwable throwable) {
            log.error("select Redis error: ",throwable.getMessage());
//            log.error("select Redis error: ",throwable);
            try {
                result = (BaseDTO)joinPoint.proceed();
            } catch (Throwable throwable1) {
                log.error("select Redis error: ",throwable1);
            }
        }

        return result;
    }

    @Around("insertCut1()")
    public void afterInsert(ProceedingJoinPoint joinPoint){
        log.info("aspect around insert to redis");

        //通过反射拿到对应method的返回值
        Class target = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();

        //
        Object[] args = joinPoint.getArgs();

        //获取参数中的泛型 可与type一并构造出带泛型的返回参数对象
        Class resultClass = Reflections.getClassGenericType(target);

        try{
            Type type = Reflections.getMethodReturnType(target,methodName,parameterTypes);
            Object retType = joinPoint.proceed();

            List list = new ArrayList();
            if(type instanceof ParameterizedType){
                System.out.println("afterInsert is true!!!");
                for (Object o:(List)retType){
                    list.add(resultClass.cast(o));
                }
            } else {
                list.add(resultClass.cast(retType));
            }

            /**
             * 目前暂时没有获取自增id方法(除了在查一遍数据库)
             * 暂时废弃
             */
            for (int i = 0;i < list.size();i++){
                BaseDTO baseDTO = (BaseDTO) list.get(i);
                System.out.println(baseDTO);
            }
        } catch (Throwable throwable){
            log.info("aspect insert error: ",throwable);
        }

    }

    public void around(){}

}
