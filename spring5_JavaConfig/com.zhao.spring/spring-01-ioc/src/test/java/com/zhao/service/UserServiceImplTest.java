package com.zhao.service;

import com.zhao.dao.UserDao;
import com.zhao.dao.UserDaoImpl;
import com.zhao.dao.UserDaoMysqlImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImplTest {
    @Test
    public void testGetUser(){
//        UserServiceImpl userService1 = new UserServiceImpl();
//        userService1.setUserDao(new UserDaoImpl());
//        userService1.getUser();
//
//        UserServiceImpl userService2 = new UserServiceImpl();
//        userService2.setUserDao(new UserDaoMysqlImpl());
//        userService2.getUser();

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserServiceImpl userService = (UserServiceImpl) context.getBean("userService");
        userService.getUser();
    }
}
