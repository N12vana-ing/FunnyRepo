package com.zykspring.funnycore.aopTest.db;

import com.zykspring.funnycore.aopTest.pojo.PojoTest;
import com.zykspring.funnycore.system.dto.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataBaseTest implements DataBaseMgr{

    public String[] getAll(List<User> list){
        System.out.println("in database get all method ");
        String[] strings = new String[]{"s:1","s:2","s:3"};
        return strings;
    }

    @Override
    public String getOne(long id) throws Exception{
        System.out.println("in database get ont method id : "+id);
        if(id == 3)
            throw new Exception();

        return "s:"+id;
    }


    public void getPojoTest(PojoTest pojoTest){
        System.out.println(pojoTest.toString() );
    }

    public void getPojoTest2(PojoTest pojo2){
        System.out.println(pojo2.toString());
    }

    @Bean(name="pojo1")
    public PojoTest pojoTest(){
        PojoTest pojoTest = new PojoTest(1,"peter");
        return pojoTest;
    }

    @Bean(name="pojo2")
    public PojoTest pojoTest2(){
        PojoTest pojoTest = new PojoTest(2,"peter2");
        return pojoTest;
    }

}
