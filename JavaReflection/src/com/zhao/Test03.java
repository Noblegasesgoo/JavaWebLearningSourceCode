package com.zhao;

import java.lang.reflect.Field;

//获得类的信息。
public class Test03 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> c1 = Class.forName("com.zhao.User");

        System.out.println(c1.getName());
        System.out.println(c1.getSimpleName());

        System.out.println(c1.getPackage());

//        Field[] fields = c1.getFields();
//        for (Field field : fields) {
//            System.out.println(field.getName());
//        }

        Field[] declaredFields = c1.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }
    }
}
