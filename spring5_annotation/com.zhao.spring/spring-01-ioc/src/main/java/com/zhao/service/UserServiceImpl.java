package com.zhao.service;

import com.zhao.dao.UserDao;
import com.zhao.dao.UserDaoImpl;

public class UserServiceImpl implements UserService{
    //private UserDao userDao = new UserDaoImpl();//要使用这个接口就接口回调一个。
    //由于我们UserDao的实现类越来越多，我们开始想办法动态生成。
    //业务层 -》 调用dao层的东西去实现dao曾的内容。
    //用户调用的是业务层，他们无法接触dao曾中的东西，安全性提高。

    private UserDao userDao;

    //这样传入的参数是谁的就调用了谁的方法。
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void getUser() {
        this.userDao.getUser();
    }
}
