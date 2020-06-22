package com.zykspring.funnycore.databaseset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {

    private static Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public ThreadLocal<String> getLocal(){ return local;}

    public static void read(){
        log.info("in read database ! ");
        local.set(DataBaseChoose.READ.choose());
    }

    public static void write(){
        log.info("in write database ! ");
        local.set(DataBaseChoose.WRITE.choose());
    }

    public static void setDataType(String type){
        log.info("----- set type is "+type+" -----");
        local.set(type);
    }

    public static void clear(){local.remove();}
    public static String getDBType(){return local.get();}

}
