package com.zykspring.funnycore.base;

public interface BaseEnum<K,V> {

    /**
     * 获取编码
     * @return
     */
    K code();

    /**
     * 获取描述
     * @return
     */
    V desc();

}
