package com.yigehui.springboot.mapper;

import com.yigehui.springboot.enty.Person;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface  PersonMapper {

    @Select("SELECT * FROM person")
    List<Person> getAll();

    @Select("SELECT * FROM person where person_id = #{id}")
    Person get(Long id);

    @Update("update person set name = #{name} where  person_id = #{id}")
    Person update(Person person);

}
