package com.zhao;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> c1 = Class.forName("com.zhao.User");

        System.out.println(c1);
    }
}

class User{
    private int No;
    public String name;

    public User() {

    }

    public User(int no, String name) {
        No = no;
        this.name = name;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    @Override
    public String toString() {
        return "User{" +
                "No=" + No +
                ", name='" + name + '\'' +
                '}';
    }
}
