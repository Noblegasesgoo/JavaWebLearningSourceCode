package com.zhao;

public class Test02 {
    public static void main(String[] args) throws ClassNotFoundException {

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);

        ClassLoader parent = systemClassLoader.getParent();
        System.out.println(parent);

        ClassLoader root = parent.getParent();
        System.out.println(root);

        //测试以下当前类是哪个类加载器加载的。

        ClassLoader classLoader = Class.forName("com.zhao.Test02").getClassLoader();
        //用用户加载器加载的。
        System.out.println(classLoader);

        ClassLoader classLoader1 = Class.forName("java.lang.Object").getClassLoader();
        //jdk内部类用的根类加载器加载的。
        System.out.println(classLoader1);

        //如何获取系统类加载器加载的路径。
        System.out.println(System.getProperty("java.class.path"));

    }
}
