package com.zykspring.funnycore.config;

import com.zykspring.funnycore.databaseset.DataBaseChoose;
import com.zykspring.funnycore.databaseset.DataBaseConfiguration;
import com.zykspring.funnycore.databaseset.ReadWriteDataSource;
import com.zykspring.funnycore.plugins.MyInterceptor;
import com.zykspring.funnycore.plugins.VersionInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnClass({EnableTransactionManagement.class})
@Import({ DataBaseConfiguration.class})
public class MyBatisConfig {

    @Resource(name = "readWriteDataSource")
    private ReadWriteDataSource readWriteDataSource;

    /**
     * Mapper扫描配置，自动扫描将Mapper接口生成代理注入到Spring
     */
    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("**.zykspring.**.mapper");
        return mapperScannerConfigurer;
    }

    /**
     * 添加乐观锁version插件
     */
    @Bean
    public Interceptor versionInterceptor(){
        return new VersionInterceptor();
    }

    /**
     * 添加测试插件
     */
    @Bean
    public Interceptor myInterceptor(){
        return new MyInterceptor();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(readWriteDataSource);
        Interceptor[] interceptors = new Interceptor[]{myInterceptor()};
        sqlSessionFactory.setPlugins(interceptors);
        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
