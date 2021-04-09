package com.zhao.demo02;

import org.junit.Test;

public class Client {
    @Test
    public void test(){
        UserServiceProxy proxy = new UserServiceProxy();

        proxy.setUserServiceImpl(new UserServiceImpl());

        proxy.insert();
    }
}
