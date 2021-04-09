package com.zhao.demo01;

import org.junit.Test;

public class Client {
    @Test
    public void test(){
        Host host = new Host();

        Proxy proxy = new Proxy(host);

        proxy.rentHouse();
    }
}
