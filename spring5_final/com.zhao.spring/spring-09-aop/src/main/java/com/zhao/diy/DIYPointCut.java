package com.zhao.diy;

//方式二：自定义切入点类。
//切面说白了就是一个类。
public class DIYPointCut {
    public void before(){
        System.out.println("方法执行之前。");
    }

    public void after(){
        System.out.println("方法执行之后。");

    }
}
