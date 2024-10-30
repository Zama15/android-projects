package com.example.localdatabase;

import java.io.Serializable;

public class Users implements Serializable {
    Integer id;
    String name;
    String lastName;
    String phone;
    Integer age;
    String sex;
    String birthday;
    Double height;

    public Users(Integer id, String name, String lastname,String phone, Integer age, String sex, String birthday, Double height) {
        this.id = id;
        this.name = name;
        this.lastName = lastname;
        this.phone = phone;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.height = height;
    }

    public Users() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
