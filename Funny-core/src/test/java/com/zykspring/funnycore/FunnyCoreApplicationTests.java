package com.zykspring.funnycore;

import com.zykspring.funnycore.aopTest.db.DataBaseTest;
import com.zykspring.funnycore.aopTest.pojo.PojoTest;
import com.zykspring.funnycore.base.Result;
import com.zykspring.funnycore.cache.RedisOperator;
import com.zykspring.funnycore.system.controller.UserController;
import com.zykspring.funnycore.system.dto.User;
import com.zykspring.funnycore.system.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FunnyCoreApplicationTests {

    @Autowired
    private DataBaseTest dataBaseTest;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @Resource(name = "pojo1")
    private PojoTest pojoTest1;

    @Autowired
    @Qualifier("pojo2")
    private PojoTest pojoTest2;




//    @Autowired(required = true)
//    private TestRestTemplate testRestTemplate;

    private URL base;

    @Before
    public void setUp(){
        try {
            this.base = new URL("http://localhost:9090/sys/user/queryOne/3");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testBeanzhujie(){
        /*AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataBaseTest.class);
        PojoTest pojoTest1 = (PojoTest)context.getBean("pojo1");
        PojoTest pojoTest2 = (PojoTest)context.getBean("pojo2");
        System.out.println(pojoTest1);
        System.out.println(pojoTest2);*/
        System.out.println("zhujie test bean in !!!!");
        System.out.println(pojoTest1);
        System.out.println(pojoTest2);
    }

    @Test
    public void contextLoads() {
//        ResponseEntity<User> responseEntity = testRestTemplate.getForEntity(base.toString(),User.class);
//        System.out.println(responseEntity.toString());

        User user = new User();
        user.setSex(0);
        user.setUsername("AspectTest1");
        user.setPassword("AspectTest1");
        user.setCreateBy((long)-1);
        user.setUpdateBy((long)-1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setNickname("AspectTest1");
        user.setEnabled(0);

        User user1 = new User();
        user1.setSex(1);
        user1.setUsername("AspectTest2");
        user1.setPassword("AspectTest2");
        user1.setCreateBy((long)-1);
        user1.setUpdateBy((long)-1);
        user1.setCreateDate(new Date());
        user1.setUpdateDate(new Date());
        user1.setNickname("AspectTest2");
        user1.setEnabled(0);


        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);

//        String[] result = new String[]{};
        try{
           String result = dataBaseTest.getOne(2);
            System.out.println(dataBaseTest.getClass());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
//
//        System.out.println(result);

//        ValueOperations<String,Object> valueOperations = redisOperator.getValueOperator();
//        valueOperations.set("user:1","user test 1");
//
//        String test = String.valueOf(valueOperations.get("test"));
//        String userstring = String.valueOf(valueOperations.get("user:1"));
//
//        System.out.println("test: "+test);
//        System.out.println("user3: "+ userstring );




//        userService.get((long)3);


    }

}
