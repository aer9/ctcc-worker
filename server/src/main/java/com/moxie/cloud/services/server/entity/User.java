package com.moxie.cloud.services.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/2/27
 */


@RequiredArgsConstructor(staticName = "of")
//@AllArgsConstructor(staticName = "all")
//@Data
public class User {
    private Long id;
    private String name;
    private int age;


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private int age;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public User build(){
            User user = new User();
            user.setAge(age);
            user.setName(name);
            return user;

        }

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
//
//    public static void main(String[] args) {
//        User user = User.builder().name("na").age(1).build();
//
//        System.out.println(user.name);
//
////        System.out.println(SubClass.value);
////        SuperClass[] superClasses = new SuperClass[3];
////        SuperClass superClass = new SuperClass();
////        System.out.println(SubClass.HELLO);
//    }
}


