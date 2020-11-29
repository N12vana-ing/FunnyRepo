package com.zykspring.funnycore;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableSwagger2Doc
@SpringBootApplication
@MapperScan("com.zykspring.funnycore.system.mapper")
public class FunnyCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunnyCoreApplication.class, args);
    }
}
