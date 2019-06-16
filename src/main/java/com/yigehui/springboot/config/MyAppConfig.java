package com.yigehui.springboot.config;

import com.yigehui.springboot.sevrvice.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Configuration 指明当前类是配置类：就是替代之前的配置文件
 *
 * 在配置文件中用<bean></bean>添加组件
 */
@Configuration
public class MyAppConfig {

    //将方法的返回值添加到容器中；容器中这个组件的ID就是方法名
    @Bean
    public HelloService helloService(){
        System.out.println("配置类@Bean给容器添加组件了...");
        return  new HelloService();
    }


}
