package com.zykspring.funnycore.databaseset;

public enum DataBaseChoose {

    READ("READ"),

    WRITE("WRITE");

    private String dataBase;
    DataBaseChoose(String dataBase){
        this.dataBase = dataBase;
    }

    public String choose(){
        return dataBase;
    }

}
