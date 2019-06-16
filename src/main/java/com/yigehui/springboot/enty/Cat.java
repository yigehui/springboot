package com.yigehui.springboot.enty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 将配置文件中的属性映射到这个组件中
 * 只有是容器中的组件，才能实现@ConfigurationProperties(prefix = "Cat")的功能
 */
//@PropertySource(value = {"classpath:cat.properties"})
@Component
@ConfigurationProperties(prefix = "cat")

public class Cat {

//    @Value("${cat.name}")
    private String name;
//    @Value("#{3*4}")
    private String age;
//    @Value("cat.city")
    private String city;
    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
