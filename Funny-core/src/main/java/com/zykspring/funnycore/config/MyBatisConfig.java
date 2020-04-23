package com.zykspring.funnycore.config;

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

}
