package com.zhao;

public class Test01 {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(A.m);
    }
}

class A{
    static int m = 100;

    static {
        System.out.println("A类静态代码块");
        m = 300;
    }

    public A() {
        System.out.println("A类的无参构造器初始化。");
    }
}