package com.moxie.cloud.services.server.entity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/2/28
 */


public class SuperClass {

    static {
        System.out.println("SuperClass 被初始化了...");
    }

    public static int value = 66;
    public static final String HELLO="HELLO WORLD";
}
