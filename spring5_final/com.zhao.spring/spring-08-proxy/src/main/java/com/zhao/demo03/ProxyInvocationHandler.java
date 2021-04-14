package com.zhao.demo03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//我们会使用它自动生产代理类。
public class ProxyInvocationHandler implements InvocationHandler {

    //被代理的接口。
    private Rent rent;

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    //生成得到代理类的对象。
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), rent.getClass().getInterfaces(), this);
    }

   //处理代理实例并返回结果。
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        this.seeHouse();

        Object result = method.invoke(rent,args);

        return result;
    }

    public void seeHouse(){
        System.out.println("中介在看房。");
    }
}
