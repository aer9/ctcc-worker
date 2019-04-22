package com.moxie.cloud.services.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/4/2
 */

@Data
public class Person {

    private Long id;
    private String name;
    @Transient
    private Integer age;


    public static PersonBuilder builder() {
        return new PersonBuilder();
    }


    public static class PersonBuilder {

        private String name;
        private Integer age;

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder age(Integer age) {
            this.age = age;
            return this;
        }


        public Person build() {
            Person person = new Person();
            person.setName(name);
            person.setAge(age);

            return person;
        }

    }

}
