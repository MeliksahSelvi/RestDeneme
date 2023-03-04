package com.meliksah.restdeneme.server.domain;

import com.meliksah.restdeneme.server.enums.Title;

public class Employee {

    private String name;
    private String surName;
    private Long age;
    private Title title;

    public Employee() {
    }

    public Employee(String name, String surName, Long age, Title title) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", age=" + age +
                ", title=" + title +
                '}';
    }
}
