package com.zykspring.funnycore.cglibTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Test
    public void testCGlib(){

        System.out.println(System.getProperty("user.dir"));

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestBean.class);
        enhancer.setCallback(new TestInterceptor());

        TestBean testBean = (TestBean)enhancer.create();

        testBean.setName("linda");
        testBean.saySth("hello");
    }


}
