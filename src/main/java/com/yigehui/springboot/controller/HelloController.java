package com.yigehui.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Map;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Map<String,Object> map){
        map.put("hello","你好11112312223231");
        map.put("users", Arrays.asList("zhangsan","lisi"));
        return "hello";
    }

}
