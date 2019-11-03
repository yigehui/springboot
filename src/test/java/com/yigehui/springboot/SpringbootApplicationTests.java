package com.yigehui.springboot;

import com.yigehui.springboot.enty.Cat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Autowired
    Cat cat;

    @Autowired
    ApplicationContext ac;

    @Autowired
    DataSource dataSource;

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

}
