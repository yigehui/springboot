package com.yigehui.springboot.controller;


import com.yigehui.springboot.enty.Person;
import com.yigehui.springboot.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private PersonMapper personMapper;

    @RequestMapping("/index")
    public List<Person> index(){

        return personMapper.getAll();
    }
}
