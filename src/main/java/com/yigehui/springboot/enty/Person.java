package com.yigehui.springboot.enty;

public class Person {


    private Integer personId;
    private String name;
    private String birthday;



    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }
}
