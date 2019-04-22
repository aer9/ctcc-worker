package com.moxie.cloud.services.server.controller;

import com.google.common.collect.Lists;
import com.moxie.cloud.services.server.entity.Person;
import org.openqa.selenium.interactions.SourceType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/4/2
 */


@RestController
@RequestMapping("/")
public class IndexController {


    @GetMapping("index")
    public Person index() {

        Person person = new Person.PersonBuilder().name("java").age(8).build();

        return person;

    }

    public static void func() {
        List<String> arrayList = Arrays.asList("a", "b", "v", "b");

        System.out.println(arrayList.indexOf("b"));
        System.out.println(arrayList.lastIndexOf("b"));

        ArrayList<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);
        Object obj = list.clone();

        System.out.println(obj);

    }

//    public static void main(String[] args) {
//
//        func();
//        int[] a = new int[8];
//        a[0] = 0;
//        a[1] = 1;
//        a[2] = 2;
//        a[3] = 3;
//        int[] c = new int[10];
//        System.arraycopy(a, 2, c, 5, 1);
//        a[2] = 99;
//        System.out.println(c.length);
//        int[] b = Arrays.copyOf(a, 10);
//        System.out.println(b.length);
//        for (int i = 0; i < c.length; i++) {
//            System.out.print(c[i] + " ");
//        }

//        add ensurecapacity ensurexpilictcapacity  grow hugecapacity

//        ArrayList<Object> list = Lists.newArrayList();
//
//        long startime = System.currentTimeMillis();
//
//        int n = 1000000;
//
//        for (int i = 0; i < n; i++) {
//            list.add(i);
//        }
//        long time = System.currentTimeMillis() - startime;
//        System.out.println("time:" + time);
//
//        List<Object> list1 = Lists.newArrayList();
//        long startime1 = System.currentTimeMillis();
//
//        list.ensureCapacity(n);
//        for (int i = 0; i < n; i++) {
//            list1.add(i);
//        }
//        long time1 = System.currentTimeMillis();
//        System.out.println(time1-startime1);
//    }

}
