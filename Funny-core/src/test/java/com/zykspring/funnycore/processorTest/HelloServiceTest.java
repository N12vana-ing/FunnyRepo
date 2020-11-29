package com.zykspring.funnycore.processorTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootTest
@Component
public class HelloServiceTest {

    @RountingInjected(value="helloServiceImpl2")
    private HelloService helloService;

    public void a(){
        helloService.sayHello();
    }

    @Test
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("com.zykspring.funnycore.processorTest");

        HelloServiceTest helloServiceTest = applicationContext.getBean(HelloServiceTest.class);
        helloServiceTest.a();
    }

}
