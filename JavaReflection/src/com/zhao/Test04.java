package com.zhao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test04 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class<?> c1 = Class.forName("com.zhao.User");


        User user = (User) c1.getDeclaredConstructor(int.class, String.class).newInstance(1,"zlm");
        System.out.println(user);

        Method setNo = c1.getDeclaredMethod("setNo", int.class);
        setNo.invoke(user, 1);
        System.out.println(user.getNo());

        User user2 = (User) c1.getDeclaredConstructor(int.class, String.class).newInstance(2, "hlw");
        System.out.println(user2);
        Field name = c1.getDeclaredField("name");
        name.set(user2, "ltw");
        System.out.println(user2);


    }
}
