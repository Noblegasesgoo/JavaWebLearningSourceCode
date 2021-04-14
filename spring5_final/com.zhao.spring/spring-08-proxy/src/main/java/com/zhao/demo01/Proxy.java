package com.zhao.demo01;

import sun.dc.pr.PRError;

public class Proxy implements Rent{
    private Host host;

    public Proxy() {
    }

    public Proxy(Host host) {
        this.host = host;
    }

    public void welcomeWord(){
        System.out.println("这里是房东！！！");
        System.out.println("欢迎你来看房子！！");
    }

    public void getFee(){
        System.out.println("收到中介费！！");
    }

    @Override
    public void rentHouse() {
        this.welcomeWord();
        this.getFee();
        this.host.rentHouse();
    }
}
