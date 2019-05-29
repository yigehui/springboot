package com.yigehui.springboot.mapper;

import com.yigehui.springboot.enty.Person;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface  PersonMapper {
    @Select("SELECT * FROM person")

    List<Person> getAll();

}
