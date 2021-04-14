package com.zhao.demo02;

public class UserServiceProxy implements UserService{
    private UserServiceImpl userServiceImpl;

    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    public void addProxyFunction(){
        System.out.println("调用了代理提供的方法。");
    }

    @Override
    public void insert() {
        this.addProxyFunction();
        userServiceImpl.insert();
    }

    @Override
    public void delete() {
        this.addProxyFunction();
        userServiceImpl.delete();
    }

    @Override
    public void update() {
        this.addProxyFunction();
        userServiceImpl.update();
    }

    @Override
    public void query() {
        this.addProxyFunction();
        userServiceImpl.query();
    }
}
