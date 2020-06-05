package com.yigehui.springboot.controller;


import com.yigehui.springboot.enty.Person;
import com.yigehui.springboot.mapper.PersonMapper;
import com.yigehui.springboot.sevrvice.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/person")
    public List<Person> persion(){

        return personService.getAll();
    }

    @GetMapping("/person/{id}")
    public Person get(@PathVariable Long id){
        return personService.get(id);
    }

}
