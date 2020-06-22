package com.zykspring.funnycore.databaseset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataBaseConfiguration {

    private Logger log = LoggerFactory.getLogger(DataBaseConfiguration.class);

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource(){
        log.info("------ write datasource init ------");
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readDataSource(){
        log.info("------ read datasource init ------");
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name="readWriteDataSource")
    public ReadWriteDataSource readWriteDataSource(){
        Map<Object,Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataBaseChoose.WRITE.choose(),writeDataSource());
        dataSourceMap.put(DataBaseChoose.READ.choose(),readDataSource());

        ReadWriteDataSource readWriteDataSource = new ReadWriteDataSource();
        readWriteDataSource.setTargetDataSources(dataSourceMap);
        readWriteDataSource.setDefaultTargetDataSource(writeDataSource());
        return readWriteDataSource;
    }

}
