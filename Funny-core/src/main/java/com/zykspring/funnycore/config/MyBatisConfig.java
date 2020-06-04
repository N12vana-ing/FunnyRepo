package com.zykspring.funnycore.config;

import com.zykspring.funnycore.plugins.MyInterceptor;
import com.zykspring.funnycore.plugins.VersionInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
public class MyBatisConfig {

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

}
