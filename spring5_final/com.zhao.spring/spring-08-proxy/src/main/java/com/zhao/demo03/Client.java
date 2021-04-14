package com.zhao.demo03;

import org.junit.Test;

public class Client {
    @Test
    public void test(){
        //真实角色。
        Host host = new Host();

        //代理角色：现在还没有：我们要通过ProxyInvocationHandler处理程序类来动态生成一个代理角色。

        //通过程序处理角色来处理我们要调用接口的对象。
        ProxyInvocationHandler pih = new ProxyInvocationHandler();//处理程序类。
        pih.setRent(host);//此时我们没有代理类生成。

        //生成代理对象。
        /*
            代理对象就是我们名字叫做“代理”的，你要代理的那个接口的一个对象罢了。
         */
        Rent proxy = (Rent) pih.getProxy();

        proxy.rentHouse();
    }
}
