package com.junit;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-8
 */
public class Person {

    private int id;
    private String name;
    private String sex;

    public Person(int id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
