package org.example.crowdfunding;

import org.example.crowdfunding.entity.Admin;
import org.example.crowdfunding.mapper.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

// 类上标记必要的注解，Spring整合Junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null, "chaoyi", "123123", "chaoyi", "chaoyi.wang@mail.com", null);
        int count = adminMapper.insert(admin);
        System.out.println("Data Inserted = "+count);
    }

    @Test
    public void testConnection() throws SQLException {
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring-persist-mybatis.xml");
        //DataSource dataSource = context.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testLog(){
        // 1.获取Logger对象，这里传入的Class就是当前打印日志的类
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        // 2.根据不同日志级别打印日志，等级 DEBUG < INFO < WARN < ERROR
        logger.debug("DEBUG!!!");
        logger.info("INFO!!!");
        logger.warn("WARN!!!");
        logger.error("ERROR!!!");
    }
}
