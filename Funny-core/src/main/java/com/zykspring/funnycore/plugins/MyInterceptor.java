package com.zykspring.funnycore.plugins;

import com.zykspring.funnycore.databaseset.DataBaseChoose;
import com.zykspring.funnycore.databaseset.DataSourceContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        ),
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        )
})
public class MyInterceptor implements Interceptor {

    //根据方法名称缓存当前连接
    private static final Map<String,DataBaseChoose> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object o1 = invocation.getTarget();
        Object[] o_args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement)o_args[0];

        if(cacheMap.get(mappedStatement.getId()) == null){
            if(mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)){
                DataSourceContextHolder.read();
                cacheMap.put(mappedStatement.getId(),DataBaseChoose.READ);
            } else {
                DataSourceContextHolder.write();
                cacheMap.put(mappedStatement.getId(),DataBaseChoose.WRITE);
            }

        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
