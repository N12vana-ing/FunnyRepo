package com.zykspring.funnycore;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableSwagger2Doc
@SpringBootApplication
public class FunnyCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunnyCoreApplication.class, args);
    }
}
