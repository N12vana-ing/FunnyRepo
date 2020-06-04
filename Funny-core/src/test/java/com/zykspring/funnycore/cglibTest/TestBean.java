package com.zykspring.funnycore.cglibTest;

public class TestBean {

    private String name;

    public TestBean(){}

    public TestBean(String name){
        this.name = name;
    }

    public void saySth(String arg){
        System.out.println(name+" say: "+arg);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
