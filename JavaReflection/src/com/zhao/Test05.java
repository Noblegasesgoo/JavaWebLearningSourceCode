package com.zhao;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//分析性能问题
public class Test05 {
    //普通方式调用
    public static void test01(){
        User user = new User();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000000000; i++) {
            user.getNo();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("普通方式执行十亿次需要时间："+(endTime - startTime));
    }

    //反射方式调用
    public static void test02() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class c1 = Class.forName("com.zhao.User");

        User user = (User) c1.getDeclaredConstructor(int.class, String.class).newInstance(1, "zlm");

        Method getNo = c1.getDeclaredMethod("getNo", null);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000000000; i++) {
            getNo.invoke(user);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("反射方式调用执行十亿次需要时间："+(endTime - startTime));
    }

    //反射方式调用 关闭检测。
    public static void test03() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class c1 = Class.forName("com.zhao.User");

        User user = (User) c1.getDeclaredConstructor(int.class, String.class).newInstance(1, "zlm");

        Method getNo = c1.getDeclaredMethod("getNo", null);
        getNo.setAccessible(true);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000000000; i++) {
            getNo.invoke(user);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("反射方式调用执行十亿次需要时间："+(endTime - startTime));
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        test01();
        test02();
        test03();
    }
}
