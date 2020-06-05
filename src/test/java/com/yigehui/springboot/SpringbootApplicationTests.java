package com.yigehui.springboot;

import com.yigehui.springboot.enty.Cat;
import com.yigehui.springboot.enty.Person;
import com.yigehui.springboot.sevrvice.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class SpringbootApplicationTests {

    @Autowired
    Cat cat;

    @Autowired
    ApplicationContext ac;

    @Autowired
    DataSource dataSource;

    @Autowired
    PersonService personService;

    @Autowired
    RedisTemplate<Object, Object> myRedisTemplate;

    @Autowired
    StringRedisTemplate myStringRedisTemplate;

    @Test
    public void testApplicationContext() {
        boolean b = ac.containsBean("helloService");
        System.out.println(b);
    }


    @Test
    public void contextLoads() {

        System.out.println(cat);
    }

    @Test
    public void testJdbc() throws SQLException {

        Connection connection = dataSource.getConnection();
        System.out.println(connection.getClass());
        connection.close();


    }


    /**
     *
     */
    @Test
    public void testRedis(){
        //Person person = personService.get(1L);
        //myRedisTemplate.opsForValue().set(1,person);
        Person person = (Person) ;
        System.out.println(person);
        System.out.println(myRedisTemplate.opsForValue().get(1));
        System.out.println(myRedisTemplate.opsForValue().get("person::1"));
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.deserialize()
    }

}
